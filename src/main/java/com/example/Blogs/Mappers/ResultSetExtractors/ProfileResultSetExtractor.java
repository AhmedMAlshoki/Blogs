package com.example.Blogs.Mappers.ResultSetExtractors;

import com.example.Blogs.Models.Post;
import com.example.Blogs.Models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class ProfileResultSetExtractor implements ResultSetExtractor<User> {
    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("display_name"),
                rs.getObject("created_at", OffsetDateTime.class)
        );
        while (rs.next()) {
               Post post = new Post(
                       rs.getLong("post_id"),
                       rs.getLong("post_user_id"),
                       rs.getString("body"),
                       rs.getString("title"),
                       rs.getObject("created_at", OffsetDateTime.class));
               user.getPosts().put(post.getId(), post);

        }
        return user;
    }
}
