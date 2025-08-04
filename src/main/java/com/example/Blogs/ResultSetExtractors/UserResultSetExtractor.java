package com.example.Blogs.ResultSetExtractors;

import com.example.Blogs.Models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {
    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<User> users = new java.util.ArrayList<>();
        while (rs.next()) {
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("display_name"),
                    rs.getObject("created_at", LocalDateTime.class)
            );
            users.add(user);
        }
        return users;
    }
}
