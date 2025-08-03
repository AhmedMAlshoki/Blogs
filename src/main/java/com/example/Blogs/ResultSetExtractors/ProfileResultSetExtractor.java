package com.example.Blogs.ResultSetExtractors;

import com.example.Blogs.Models.Post;
import com.example.Blogs.Models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ProfileResultSetExtractor implements ResultSetExtractor<User> {
    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getObject("created_at", LocalDateTime.class)
        );
        while (rs.next()) {
            user.getPosts().add(new Post(
                    rs.getLong("post_id"),
                    rs.getLong("id"),
                    rs.getString("body"),
                    rs.getString("title"),
                    rs.getTimestamp("published_at").toLocalDateTime()
            ));
        }
        return user;
    }
}
