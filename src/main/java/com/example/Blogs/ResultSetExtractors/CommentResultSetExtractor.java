package com.example.Blogs.ResultSetExtractors;

import com.example.Blogs.Models.Comment;
import com.example.Blogs.Models.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentResultSetExtractor implements ResultSetExtractor<Map<Long, Post>> {
    Map<Long, Post> posts;
    public CommentResultSetExtractor(Map<Long, Post> posts)
    {
        this.posts = posts;
    }
    @Override
    public Map<Long, Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()) {
            if (rs.getLong("c.comment_id") == 0)
                continue;
            Comment comment = new Comment(
                    rs.getString("body"),
                    rs.getLong("comment_post_id"),
                    rs.getLong("comment_user_id"),
                    rs.getTimestamp("created_at").toLocalDateTime());
            posts.get(rs.getLong("comment_post_id")).getComments().add(comment);
        }
        return posts;
    }
}
