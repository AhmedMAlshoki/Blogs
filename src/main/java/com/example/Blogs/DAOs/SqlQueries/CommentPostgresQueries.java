package com.example.Blogs.DAOs.SqlQueries;

import com.example.Blogs.DAOs.DAOUtilities.DAOUtilities;

public class CommentPostgresQueries extends CommentQueries {

    public CommentPostgresQueries() {
        super();
    }

    @Override
    public String findById() {
        return "SELECT * FROM comments WHERE id = $1;";
    }
    @Override
    public String insertQuery() {
        return "INSERT INTO comments (user_id, post_id, body) VALUES ($1 , $2, $3);";
    }

    @Override
    public String updateQuery() {
        return "UPDATE comments SET body = $1 WHERE id = $2;";
    }

    @Override
    public String deleteQuery() {
        return "DELETE FROM comments WHERE id = $1;";
    }


    @Override
    public String existsById() {
        return "SELECT EXISTS(SELECT 1 FROM comments WHERE id = $1 )";
    }

    @Override
    public String SQLQueryForFindByPost() {
        return "SELECT user_id, body, created_at FROM comments WHERE post_id = $1;";
    }

    @Override
    public String SQLQueryForFindByUser() {
        return "SELECT post_id, body, created_at FROM comments WHERE post_id = $1;";
    }

    @Override
    public String SQLQueryForFindByMultiplePosts() {
        return "SELECT * FROM comments WHERE post_id = ANY($1);";
    }
}
