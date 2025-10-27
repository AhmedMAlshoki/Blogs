package com.example.Blogs.Mappers.RowMappers;

import com.example.Blogs.Models.User;
import com.example.Blogs.Utils.TimeDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            log.info("Mapping user row id {}",rs.getLong("id"));
            log.info("Mapping user row  email {}",rs.getString("email"));
            log.info("Mapping user row password {}",rs.getString("password"));
            User user = new User(
                   rs.getLong("id"),
                   rs.getString("email"),
                   rs.getString("password"));
            log.info("Mapping user row FINAL OBJECT  {}",user);
            return user;
        } catch (Exception e) {
            log.info(e.getMessage());
            TimeDateUtil timeDateUtil = new TimeDateUtil();
            OffsetDateTime offsetDateTime = timeDateUtil.formatOffsetDateTime(rs.getObject("created_at", OffsetDateTime.class).toString());
            return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("display_name"),
                    offsetDateTime);
        }
    }
}
