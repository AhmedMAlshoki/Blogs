-- Enable necessary extensions
--pg_trgm: Enables fuzzy search and similarity matching. Essential for handling misspellings and finding similar terms.
--unaccent: Removes diacritics from text, improving search for international content.

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE EXTENSION IF NOT EXISTS unaccent;

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
    created_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
);

--users table
CREATE TABLE  IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    display_name VARCHAR(255),
    email VARCHAR(255) UNIQUE UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    num_of_posts INTEGER DEFAULT 0,
    num_of_followers INTEGER DEFAULT 0,
    num_of_following INTEGER DEFAULT 0
);
--likes table
CREATE TABLE  IF NOT EXISTS likes (
    id SERIAL PRIMARY KEY,
    user_id SERIAL REFERENCES users(id),
    post_id SERIAL REFERENCES posts(id),
    created_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

--comments table
CREATE TABLE  IF NOT EXISTS comments (
    id INTEGER PRIMARY KEY,
    user_id SERIAL REFERENCES users(id) ON DELETE CASCADE,
    post_id SERIAL REFERENCES posts(id) ON DELETE CASCADE,
    body TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH  TIME ZONE DEFAULT CURRENT_TIMESTAMP,
);
--relationship table
CREATE TABLE  IF NOT EXISTS relationships (
    id SERIAL PRIMARY KEY,
    follower_id SERIAL REFERENCES users(id) ON DELETE CASCADE,
    following_id SERIAL REFERENCES users(id) ON DELETE CASCADE
);

--to get user's posts quickly using index
CREATE INDEX user_posts ON posts(user_id);
--to get post's likes quickly using index
CREATE INDEX post_likes ON likes(post_id);
--to get post's comments quickly using index
CREATE INDEX post_comments ON comments(post_id);
--to get user's followers and following quickly
CREATE INDEX user_following_list ON relationships(follower_id);
CREATE INDEX user_followers_list ON relationships(following_id);
--to find is user is following another user or vise versa
CREATE UNIQUE INDEX user_relationships_unique ON relationships (follower_id, following_id);
--for efficient search
CREATE INDEX posts_search_idx ON posts USING GIN (search_vector);

--to get user's posts quickly using index
CREATE TYPE search_result AS (
  id INTEGER,
  title VARCHAR(255),
  body TEXT,
  user_name VARCHAR(255),
  published_at TIMESTAMP WITH TIME ZONE,
  rank FLOAT,
  highlight TEXT
);
-- Create updated_at trigger
CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER posts_updated_at
    BEFORE UPDATE ON posts
    FOR EACH ROW
        EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER comments_updated_at
    AFTER UPDATE ON comments
    FOR EACH ROW
        EXECUTE FUNCTION update_updated_at();

CREATE TRIGGER user_updated_at
    AFTER UPDATE ON users
    FOR EACH ROW
        EXECUTE FUNCTION update_updated_at();
-- Create created_at trigger Comments
CREATE OR REPLACE FUNCTION created_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.created_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER posts_created_at
    AFTER UPDATE ON posts
    FOR EACH ROW
        EXECUTE FUNCTION created_at();

CREATE TRIGGER users_created_at
    AFTER UPDATE ON users
    FOR EACH ROW
        EXECUTE FUNCTION created_at();

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
    ) AS $$
          DECLARE
          tsquery_var tsquery;
          total BIGINT;
    BEGIN
       -- Convert search query to tsquery, handling multiple words
        SELECT array_to_string(array_agg(lexeme || ':*'), ' & ')
        FROM unnest(regexp_split_to_array(trim(search_query), '\s+')) lexeme
        INTO search_query;
        tsquery_var := to_tsquery('english', search_query);
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
                                  WHEN p.created_at > NOW() - INTERVAL '7 days' THEN 1.5  -- Boost recent articles
                                  WHEN p.created_at > NOW() - INTERVAL '30 days' THEN 1.2
                                  ELSE 1.0
                                  END as rank,
                              ts_headline('english', p.body, tsquery_var, 'StartSel=<mark>, StopSel=</mark>, MaxFragments=1, MaxWords=50, MinWords=20') as highlight
                     FROM posts p
                     JOIN users u  ON p.user_id = u.id
                     AND p.search_vector @@ tsquery_var
                     AND (author_filter IS NULL OR u.id = ANY(author_filter))
                     AND (min_date IS NULL OR p.created_at >= min_date)
                     AND (max_date IS NULL OR p.created_at <= max_date)
               )
               SELECT
                     ra.id,
                     ra.title,
                     ra.subtitle,
                     ra.author_name,
                     ra.published_at,
                     ra.rank,
                     ra.highlight,
                     total as total_count
               FROM ranked_articles ra
               ORDER BY ra.rank DESC
               LIMIT page_size
               OFFSET (page_number - 1) * page_size;
    END;
$$ LANGUAGE plpgsql;

