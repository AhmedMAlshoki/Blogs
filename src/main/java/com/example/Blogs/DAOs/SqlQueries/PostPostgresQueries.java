package com.example.Blogs.DAOs.SqlQueries;

import java.util.List;

public  class  PostPostgresQueries extends PostQueries {

    public PostPostgresQueries() {
        super();
    }
    private  String DynamicINSql(Integer size) {
        StringBuilder sql =new StringBuilder("IN (");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sql.append("?");
            } else {
                sql.append("?,");
            }
        }
        sql.append(")");
        return sql.toString();
    }

    public  String existsById(Long id) {
        return "SELECT EXISTS(SELECT 1 FROM posts WHERE id = ?)";
    }

    public  String SqlQueryForFindingASinglePost() {
        return """
               WITH post_data AS (
                    SELECT\s
                        p.id AS post_id,
                        p.user_id AS post_user_id,
                        p.body AS post_body,
                        p.title AS post_title,
                        p.created_at AS post_created_at,
                        p.number_of_likes,
                        p.number_of_comments
                    FROM posts p\s
                    WHERE p.id = ?
                ),
                numbered_likes AS (
                    SELECT\s
                        l.id AS like_id,
                        l.user_id AS like_user_id,
                        l.created_at AS like_created_at,
                        ROW_NUMBER() OVER (ORDER BY l.id) AS rn
                    FROM likes l
                    WHERE l.post_id = ?
                ),
                numbered_comments AS (
                    SELECT\s
                        c.id AS comment_id,
                        c.user_id AS comment_user_id,
                        c.body AS comment_body,
                        c.created_at AS comment_created_at,
                        ROW_NUMBER() OVER (ORDER BY c.id) AS rn
                    FROM comments c\s
                    WHERE c.post_id = ?
                ),
                engagement_rows AS (
                    SELECT rn FROM numbered_likes
                    UNION
                    SELECT rn FROM numbered_comments
                ),
                max_engagement AS (
                    SELECT COALESCE(MAX(rn), 0) AS max_rn FROM engagement_rows
                ),
                row_sequence AS (
                    SELECT generate_series(1, (SELECT max_rn FROM max_engagement)) AS rn
                )
                SELECT\s
                    CASE WHEN seq.rn = 1 THEN pd.post_id END AS post_id,
                    CASE WHEN seq.rn = 1 THEN pd.post_user_id END AS post_user_id,
                    CASE WHEN seq.rn = 1 THEN pd.post_body END AS post_body,
                    CASE WHEN seq.rn = 1 THEN pd.post_title END AS post_title,
                    CASE WHEN seq.rn = 1 THEN pd.post_created_at END AS post_created_at,
                    CASE WHEN seq.rn = 1 THEN pd.number_of_likes END AS number_of_likes,
                    CASE WHEN seq.rn = 1 THEN pd.number_of_comments END AS number_of_comments,
                    l.like_id,
                    l.like_user_id,
                    l.like_created_at,
                    c.comment_id,
                    c.comment_user_id,
                    c.comment_body,
                    c.comment_created_at
                FROM row_sequence seq
                LEFT JOIN post_data pd ON seq.rn = 1
                LEFT JOIN numbered_likes l ON seq.rn = l.rn
                LEFT JOIN numbered_comments c ON seq.rn = c.rn
                ORDER BY seq.rn;""";

    }

    public  String SqlQueryForFindingAllPostsIdsByUser() {
        return "SELECT id FROM posts WHERE user_id = ?";
    }

    public  String SqlQueryForFindingAllPosts(List<Long> ids) {
        return "WITH posts AS (\n" +
                "    SELECT * " +
                "    FROM posts " +
                "    WHERE id " + this.DynamicINSql(ids.size())  +
                ")"+
                "numbered_engagements AS (" +
                "    SELECT " +
                "        e.post_id" +
                "        e.type," +
                "        e.id," +
                "        e.user_id," +
                "        e.body," +
                "        e.created_at," +
                "        ROW_NUMBER() OVER (" +
                "            PARTITION BY e.post_id, e.type " +
                "            ORDER BY e.created_at" +
                "        ) AS rn" +
                "    FROM (" +
                "        SELECT " +
                "            post_id, " +
                "            'like' AS type," +
                "            id," +
                "            user_id," +
                "            NULL AS body," +
                "            created_at" +
                "        FROM likes" +
                "        WHERE post_id " + this.DynamicINSql(ids.size()) + "\n" +
                "        UNION ALL" +
                "        \n" +
                "        SELECT " +
                "            post_id, " +
                "            'comment' AS type," +
                "            id," +
                "            user_id," +
                "            body," +
                "            created_at" +
                "        FROM comments" +
                "        WHERE post_id " + this.DynamicINSql(ids.size()) + "\n" +
                "    ) e\n" +
                ")"+
                "post_sequences AS (" +
                "    SELECT " +
                "        p.id AS post_id," +
                "        COALESCE(MAX(e.rn), 0) AS max_rn" +
                "    FROM posts p" +
                "    LEFT JOIN numbered_engagements e ON p.id = e.post_id" +
                "    GROUP BY p.id" +
                ")"+
                "all_rows AS (" +
                "    SELECT" +
                "        p.id AS post_id," +
                "        seq.rn," +
                "        p.user_id AS post_user_id," +
                "        p.body AS post_body," +
                "        p.title AS post_title," +
                "        p.created_at AS post_created_at," +
                "        p.number_of_likes," +
                "        p.number_of_comments" +
                "    FROM posts p" +
                "    CROSS JOIN LATERAL (" +
                "        SELECT generate_series(1, GREATEST(ps.max_rn, 1)) AS rn" +
                "        FROM post_sequences ps" +
                "        WHERE ps.post_id = p.id" +
                "    ) seq" +
                ")"+
                "SELECT \n" +
                "    CASE WHEN ar.rn = 1 THEN ar.post_id END AS post_id," +
                "    CASE WHEN ar.rn = 1 THEN ar.post_user_id END AS post_user_id," +
                "    CASE WHEN ar.rn = 1 THEN ar.post_body END AS post_body," +
                "    CASE WHEN ar.rn = 1 THEN ar.post_title END AS post_title," +
                "    CASE WHEN ar.rn = 1 THEN ar.post_created_at END AS post_created_at," +
                "    CASE WHEN ar.rn = 1 THEN ar.number_of_likes END AS number_of_likes," +
                "    CASE WHEN ar.rn = 1 THEN ar.number_of_comments END AS number_of_comments," +
                "    ne_like.id AS like_id," +
                "    ne_like.user_id AS like_user_id," +
                "    ne_like.created_at AS like_created_at," +
                "    ne_comment.id AS comment_id," +
                "    ne_comment.user_id AS comment_user_id," +
                "    ne_comment.body AS comment_body," +
                "    ne_comment.created_at AS comment_created_at" +
                "FROM all_rows ar" +
                "LEFT JOIN numbered_engagements ne_like " +
                "    ON ar.post_id = ne_like.post_id " +
                "    AND ar.rn = ne_like.rn" +
                "    AND ne_like.type = 'like'" +
                "LEFT JOIN numbered_engagements ne_comment " +
                "    ON ar.post_id = ne_comment.post_id " +
                "    AND ar.rn = ne_comment.rn" +
                "    AND ne_comment.type = 'comment'" +
                "ORDER BY ar.post_id, ar.rn;";
    }

    public  String insertQuery() {
        return "INSERT INTO posts (user_id, body, title) VALUES (?, ?, ?)";
    }

    public  String updateQuery() {
        return "UPDATE posts SET body = ?,title = ?,WHERE id = ?;" ;
    }

    public  String deleteQuery() {
        return "DELETE FROM posts WHERE id = ?;";
    }
}
