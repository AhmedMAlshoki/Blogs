package com.example.Blogs.RowMappers;

import com.example.Blogs.Models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getObject("signed_up_at", LocalDateTime.class),
                rs.getInt("num_of_posts"),
                rs.getInt("num_of_followers"),
                rs.getInt("num_of_following")
        );
    }
}
