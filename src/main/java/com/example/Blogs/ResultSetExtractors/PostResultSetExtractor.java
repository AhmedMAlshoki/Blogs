package com.example.Blogs.ResultSetExtractors;

import com.example.Blogs.Models.Comment;
import com.example.Blogs.Models.Like;
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
        Long postId = rs.getLong("post_id");
        while (rs.next()) {
            if(rs.getObject("post_id") != null) {
                if (!posts.containsKey(rs.getLong("post_id")))
                {
                    Post post = new Post(
                            rs.getLong("post_id"),
                            rs.getLong("post_user_id"),
                            rs.getString("body"),
                            rs.getString("title"),
                            rs.getTimestamp("created_at").toLocalDateTime());
                    postId = post.getId();
                    posts.put(post.getId(), post);
                }
                if (rs.getObject("like_id") != null)
                {
                    Like like = new Like(rs.getLong("like_user_id"),
                                         rs.getTimestamp("like_created_at").toLocalDateTime());
                    posts.get(postId).getLikes().add(like);
                }
            }
        }
        return posts;
    }
}
