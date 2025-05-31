package com.example.Blogs.ResultSetExtractors;

import com.example.Blogs.Models.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PostResultSetExtractor implements ResultSetExtractor<Map<Long, Post>> {
    @Override
    public Map<Long, Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Post> posts = new java.util.HashMap<>();
        while (rs.next()) {
            if (posts.containsKey(rs.getLong("post_id")))
                continue;
            Post post = new Post(
                    rs.getLong("post_id"),
                    rs.getLong("post_user_id"),
                    rs.getString("body"),
                    rs.getString("title"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getInt("number_of_likes"),
                    rs.getInt("number_of_comments"));
            posts.put(post.getId(), post);
        }
        return posts;
    }
}
