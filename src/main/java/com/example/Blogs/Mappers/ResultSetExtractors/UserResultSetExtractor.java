package com.example.Blogs.Mappers.ResultSetExtractors;

import com.example.Blogs.Models.User;
import com.example.Blogs.Utils.TimeDateUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<User> users = new java.util.ArrayList<>();
        TimeDateUtil timeDateUtil = new TimeDateUtil();
        while (rs.next()) {
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("display_name")
            );
            OffsetDateTime offsetDateTime = timeDateUtil.formatOffsetDateTime(rs.getObject("created_at", OffsetDateTime.class).toString());            user.setSignedUpAt(offsetDateTime);
            users.add(user);
        }
        return users;
    }
}
