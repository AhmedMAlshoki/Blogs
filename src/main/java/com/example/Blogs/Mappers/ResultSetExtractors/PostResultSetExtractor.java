package com.example.Blogs.Mappers.ResultSetExtractors;

import com.example.Blogs.Models.Comment;
import com.example.Blogs.Models.Like;
import com.example.Blogs.Models.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class PostResultSetExtractor implements ResultSetExtractor<Map<Long, Post>> {
    @Override
    public Map<Long, Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Post> posts = new java.util.HashMap<>();
        Long postId = null;
        log.info("Start Mapping ");
        while (rs.next()) {
            if(rs.getObject("post_id") != null) {
                log.info("Mapping to Post id: {}", rs.getLong("post_id"));
                if (!posts.containsKey(rs.getLong("post_id")))
                {
                    Post post = new Post(
                            rs.getLong("post_id"),
                            rs.getLong("post_user_id"),
                            rs.getString("body"),
                            rs.getString("title"),
                            rs.getObject("created_at", OffsetDateTime.class));
                    postId = post.getId();
                    posts.put(post.getId(), post);
                }
                if (rs.getObject("like_id") != null)
                {
                    Like like = new Like(rs.getLong("like_user_id"),
                            rs.getObject("created_at", OffsetDateTime.class));
                    posts.get(postId).getLikes().add(like);
                }
            }
        }
        return posts;
    }
}
