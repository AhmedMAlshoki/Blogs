package com.example.Blogs.DAOs.SqlQueries;

import java.util.List;

public abstract class PostQueries {
    public abstract String existsById(Long id);
    public abstract String SqlQueryForFindingASinglePost();
    public abstract String SqlQueryForFindingAllPostsIdsByUser();
    public abstract String SqlQueryForFindingAllPosts(List<Long> ids);
    public abstract String insertQuery();
    public abstract String updateQuery();
    public abstract String deleteQuery();
}
