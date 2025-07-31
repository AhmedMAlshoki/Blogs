package com.example.Blogs.DAOs.SqlQueries;

import java.util.List;

public abstract class PostQueries {
    public abstract String existsById(Long id);
    public abstract String SqlQueryForFindingAllPostsIdsByUser();
    public abstract String SqlQueryForFindingMultiplePosts(Object[] ids);
    public abstract String SQLQueryForTopPostsIds(Integer Offset);
    public abstract String SQLQueryForCurrentUserFollowingPosts(Long id);
    public abstract String SQLQueryForPostSearch(String searchQuery, List<Long> authorFilter, String minDate, String maxDate, Integer limit, Integer offset);
    public abstract String insertQuery();
    public abstract String updateQuery();
    public abstract String deleteQuery();
}
