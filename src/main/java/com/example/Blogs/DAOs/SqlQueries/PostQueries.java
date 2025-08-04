package com.example.Blogs.DAOs.SqlQueries;

import java.util.List;

public abstract class PostQueries {
    public abstract String existsById();
    public abstract String SqlQueryForFindingAllPostsByUser();
    public abstract String SqlQueryForFindingOnePostOrMultiple();
    public abstract String SQLQueryForTopPosts();
    public abstract String SQLQueryForCurrentUserFollowingPosts();
    public abstract String SQLQueryForPostSearch();
    public abstract String insertQuery();
    public abstract String updateQuery();
    public abstract String deleteQuery();
}
