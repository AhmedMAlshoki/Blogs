-- Enable necessary extensions
--pg_trgm: Enables fuzzy search and similarity matching. Essential for handling misspellings and finding similar terms.
--unaccent: Removes diacritics from text, improving search for international content.

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE EXTENSION IF NOT EXISTS unaccent;
--users table
CREATE TABLE  IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    display_name VARCHAR(255),
    email VARCHAR(255) UNIQUE UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH  TIME ZONE ,
    created_timezone VARCHAR(255) DEFAULT 'UTC',
    updated_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_timezone VARCHAR(255) DEFAULT 'UTC'
);
--posts table
CREATE TABLE  IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    body TEXT,
    title VARCHAR(255),
    search_vector tsvector GENERATED ALWAYS AS (
                                           setweight(to_tsvector('english', coalesce(title, '')), 'A') ||
                                           setweight(to_tsvector('english', coalesce(body, '')), 'B'))
        STORED,
    score INTEGER DEFAULT 0,
    created_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_timezone VARCHAR(255) DEFAULT 'UTC',
    updated_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_timezone VARCHAR(255)
);


--likes table
CREATE TABLE  IF NOT EXISTS likes (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    post_id INTEGER REFERENCES posts(id),
    created_at TIMESTAMP WITH  TIME ZONE,
    created_timezone VARCHAR(255) DEFAULT 'UTC'
);

--comments table
CREATE TABLE  IF NOT EXISTS comments (
    id INTEGER PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    body TEXT,
    created_at TIMESTAMP WITH TIME ZONE ,
    created_timezone VARCHAR(255) DEFAULT 'UTC',
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_timezone VARCHAR(255) DEFAULT 'UTC'
);
--relationship table
CREATE TABLE  IF NOT EXISTS relationships (
    id SERIAL PRIMARY KEY,
    follower_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    following_id INTEGER REFERENCES users(id) ON DELETE CASCADE
);

--to get user's posts quickly using index
CREATE INDEX IF NOT EXISTS user_posts ON posts(user_id);
--to get post's likes quickly using index
CREATE INDEX IF NOT EXISTS post_likes ON likes(post_id);
--to get post's comments quickly using index
CREATE INDEX IF NOT EXISTS post_comments ON comments(post_id);
--to get user's followers and following quickly
CREATE INDEX  IF NOT EXISTS user_following_list ON relationships(follower_id);
CREATE INDEX IF NOT EXISTS user_followers_list ON relationships(following_id);
--to find is user is following another user or vise versa
CREATE UNIQUE INDEX IF NOT EXISTS user_relationships_unique ON relationships (follower_id, following_id);
CREATE UNIQUE INDEX IF NOT EXISTS user_like_post_unique ON likes (user_id, post_id);
--for efficient search
CREATE INDEX IF NOT EXISTS posts_search_idx ON posts USING GIN (search_vector);

--to get user's posts quickly using index
DO '
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = ''search_result'') THEN
        CREATE TYPE search_result AS (
            id INTEGER,
            title VARCHAR(255),
            body TEXT,
            user_name VARCHAR(255),
            published_at TIMESTAMPTZ,
            rank FLOAT,
            highlight TEXT
        );
    END IF;
END ' LANGUAGE plpgsql;


--Trigger Function to update score of post
 CREATE OR REPLACE FUNCTION update_post_score()
    RETURNS TRIGGER AS '
    BEGIN
        -- Example: Insert into another_table when a new row is inserted into the triggered table
        IF TG_OP = ''INSERT'' THEN
            UPDATE posts SET score = score + 1 WHERE id = NEW.post_id;
            RETURN NEW;
        END IF;
        -- Example: Delete from another_table when a row is deleted from the triggered table
        IF TG_OP = ''DELETE'' THEN
            UPDATE posts SET score = score - 1 WHERE id = NEW.post_id;
            RETURN OLD;
        END IF;
        RETURN NULL;
    END;
    ' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS posts_score_update_likes ON likes;
CREATE TRIGGER posts_score_update_likes
    AFTER DELETE OR INSERT ON likes
    FOR EACH ROW
        EXECUTE FUNCTION update_post_score();

DROP TRIGGER IF EXISTS posts_score_update_comments ON comments;
CREATE TRIGGER posts_score_update_comments
    AFTER DELETE OR INSERT ON comments
    FOR EACH ROW
        EXECUTE FUNCTION update_post_score();
-- Create updated_at trigger
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS '
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
'
LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS posts_updated_at ON posts;
CREATE TRIGGER posts_updated_at
    BEFORE UPDATE ON posts
    FOR EACH ROW
        EXECUTE FUNCTION update_updated_at();

DROP TRIGGER IF EXISTS comments_updated_at ON comments;
CREATE TRIGGER comments_updated_at
    AFTER UPDATE ON comments
    FOR EACH ROW
        EXECUTE FUNCTION update_updated_at();

DROP TRIGGER IF EXISTS user_updated_at ON users;
CREATE TRIGGER user_updated_at
    AFTER UPDATE ON users
    FOR EACH ROW
        EXECUTE FUNCTION update_updated_at();
-- Create created_at trigger Comments

CREATE OR REPLACE FUNCTION created_at()
RETURNS TRIGGER AS '
BEGIN
    NEW.created_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
'
LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS posts_created_at ON posts;
CREATE TRIGGER posts_created_at
    AFTER UPDATE ON posts
    FOR EACH ROW
        EXECUTE FUNCTION created_at();

DROP TRIGGER IF EXISTS users_created_at ON users;
CREATE TRIGGER users_created_at
    AFTER UPDATE ON users
    FOR EACH ROW
        EXECUTE FUNCTION created_at();

DROP TRIGGER IF EXISTS comments_created_at ON comments;
CREATE TRIGGER comments_created_at
    AFTER UPDATE ON comments
    FOR EACH ROW
        EXECUTE FUNCTION created_at();
--The Search Articles Function 
CREATE OR REPLACE FUNCTION search_articles(
    search_query TEXT,
    author_filter INTEGER[] DEFAULT NULL,
    min_date TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    max_date TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    page_size INTEGER DEFAULT 20,
    page_number INTEGER DEFAULT 1
    ) RETURNS TABLE (
              results search_result,
              total_count BIGINT
    ) AS '
          DECLARE
          tsquery_var tsquery;
          total BIGINT;
    BEGIN
       -- Convert search query to tsquery, handling multiple words
        SELECT array_to_string(array_agg(lexeme || '':*''), '' & '')
        FROM unnest(regexp_split_to_array(trim(search_query), ''\s+'')) lexeme
        INTO search_query;
        tsquery_var := to_tsquery(''english'', search_query);
        -- Get total count for pagination
        SELECT COUNT(DISTINCT p.id)
        FROM posts p
        WHERE p.search_vector @@ tsquery_var
        AND (author_filter IS NULL OR p.user_id = ANY(author_filter))
        AND (min_date IS NULL OR p.created_at >= min_date)
        AND (max_date IS NULL OR p.created_at <= max_date)
        INTO total;

        RETURN QUERY
               WITH ranked_articles AS (
                     SELECT DISTINCT ON (p.id)
                             p.id,
                             p.title,
                             p.body,
                             u.username as user_name,
                             p.created_at as published_at,
                             ts_rank(p.search_vector, tsquery_var) *
                             CASE
                                  WHEN p.created_at > NOW() - INTERVAL ''7 days'' THEN 1.5  -- Boost recent articles
                                  WHEN p.created_at > NOW() - INTERVAL ''30 days'' THEN 1.2
                                  ELSE 1.0
                                  END as rank,
                              ts_headline(''english'', p.body, tsquery_var, ''StartSel=<mark>, StopSel=</mark>, MaxFragments=1, MaxWords=50, MinWords=20'') as highlight
                     FROM posts p
                     JOIN users u  ON p.user_id = u.id
                     AND p.search_vector @@ tsquery_var
                     AND (author_filter IS NULL OR u.id = ANY(author_filter))
                     AND (min_date IS NULL OR p.created_at >= min_date)
                     AND (max_date IS NULL OR p.created_at <= max_date)
               ),
               likes_for_ranking AS (
                     SELECT likes.post_id as id , likes.user_id as user_id , likes.post_id as post_id
                     FROM likes
                     WHERE likes.post_id IN (
                         SELECT id FROM ranked_articles
                     )
               )
               SELECT
                     ra.id,
                     ra.title,
                     ra.subtitle,
                     ra.author_name,
                     ra.published_at,
                     ra.rank,
                     ra.highlight,
                     total as total_count,
                     likes_for_ranking.user_id,
                     DENSE_RANK() OVER (PARTITION BY ra.id) as rank_order
               FROM ranked_articles ra
               LEFT JOIN likes_for_ranking ON ra.id = likes_for_ranking.post_id
               ORDER BY ra.rank DESC
               LIMIT page_size
               OFFSET (page_number - 1) * page_size;
    END;
'
LANGUAGE plpgsql;

