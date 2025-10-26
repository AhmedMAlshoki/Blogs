package com.example.Blogs.Mappers.RowMappers;

import com.example.Blogs.Models.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime dataBaseDate = LocalDateTime.parse(rs.getObject("created_at", OffsetDateTime.class).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        OffsetDateTime offsetDateTime = OffsetDateTime.of(dataBaseDate, ZoneId.systemDefault().getRules().getOffset(dataBaseDate));
        Comment comment = new Comment(
                rs.getLong("id"),
                rs.getString("body"),
                rs.getLong("post_id"),
                rs.getLong("user_id")
        );
        comment.setCreatedAt(offsetDateTime);
        return comment;

    }
}
