package com.example.Blogs.DAOs.SqlQueries;

import com.example.Blogs.Models.Comment;

import java.util.List;

public abstract class CommentQueries {
    public abstract String findById();
    public abstract String insertQuery();
    public abstract String updateQuery();
    public abstract String deleteQuery();
    public abstract String existsById();
    public abstract String SQLQueryForFindByPost();
    public abstract String SQLQueryForFindByUser();
    public abstract String SQLQueryForFindByMultiplePosts();
}
