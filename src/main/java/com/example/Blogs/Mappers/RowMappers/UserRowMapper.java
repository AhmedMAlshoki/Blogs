package com.example.Blogs.Mappers.RowMappers;

import com.example.Blogs.Models.User;
import com.example.Blogs.Utils.TimeDateUtil;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimeDateUtil timeDateUtil = new TimeDateUtil();
        try {
            return new User(
                   rs.getLong("id"),
                   rs.getString("email"),
                   rs.getString("password"));
        } catch (Exception e) {
            OffsetDateTime offsetDateTime = timeDateUtil.formatOffsetDateTime(rs.getObject("created_at", OffsetDateTime.class).toString());
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("display_name"));
          user.setSignedUpAt(offsetDateTime);
          return user;
        }
    }
}
