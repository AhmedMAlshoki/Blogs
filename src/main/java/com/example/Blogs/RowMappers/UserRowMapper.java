package com.example.Blogs.RowMappers;

import com.example.Blogs.Models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getString("email") != null) {
            return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password"));
    }
        else return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getObject("created_at", OffsetDateTime.class));
    }
}
