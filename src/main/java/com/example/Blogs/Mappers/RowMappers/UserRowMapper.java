package com.example.Blogs.Mappers.RowMappers;

import com.example.Blogs.Models.User;
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

        try {
             User user = new User(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"));
             return user;
        } catch (Exception e) {
          LocalDateTime dataBaseDate = LocalDateTime.parse(rs.getObject("created_at", OffsetDateTime.class).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
          OffsetDateTime offsetDateTime = OffsetDateTime.of(dataBaseDate, ZoneId.systemDefault().getRules().getOffset(dataBaseDate));
          User user = new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("display_name"));
          user.setSignedUpAt(offsetDateTime);
          return user;
        }
    }
}
