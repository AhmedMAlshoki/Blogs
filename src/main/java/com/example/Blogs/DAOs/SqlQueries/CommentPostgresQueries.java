package com.example.Blogs.DAOs.SqlQueries;

public class CommentPostgresQueries extends CommentQueries {

    public CommentPostgresQueries() {
        super();
    }

    @Override
    public String findById() {
        return "SELECT * FROM comments WHERE id = ?;";
    }
    @Override
    public String insertQuery() {
        return "INSERT INTO comments (user_id, post_id, body, created_timezone) VALUES (? , ?, ?, ?);";
    }

    @Override
    public String updateQuery() {
        return "UPDATE comments SET body = ? , updated_timezone = ? WHERE id = ?;";
    }

    @Override
    public String deleteQuery() {
        return "DELETE FROM comments WHERE id = ?;";
    }


    @Override
    public String existsById() {
        return "SELECT EXISTS(SELECT 1 FROM comments WHERE id = ? )";
    }

    @Override
    public String SQLQueryForFindByPost() {
        return "SELECT user_id, body, created_at FROM comments WHERE post_id = ?;";
    }

    @Override
    public String SQLQueryForFindByUser() {
        return "SELECT post_id, body, created_at FROM comments WHERE post_id = ?;";
    }

    @Override
    public String SQLQueryForFindByMultiplePosts() {
        return "SELECT * FROM comments WHERE post_id = ANY(?);";
    }

    @Override
    public String getCommentOwnerQuery() {
        return "SELECT user_id FROM comments WHERE id = ?;";
    }
}
