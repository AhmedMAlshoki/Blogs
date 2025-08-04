package com.example.Blogs.RowMappers;

import com.example.Blogs.Models.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
                rs.getLong("id"),
                rs.getString("body"),
                rs.getLong("comment_post_id"),
                rs.getLong("comment_user_id"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
