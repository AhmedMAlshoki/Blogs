package com.example.Blogs.Mappers.ResultSetExtractors;

import com.example.Blogs.Models.Post;
import com.example.Blogs.Models.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ProfileResultSetExtractor implements ResultSetExtractor<User> {
    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = null;
        while (rs.next()) {
            if (user == null) {
                LocalDateTime dataBaseDate = LocalDateTime.parse(rs.getObject("created_at", OffsetDateTime.class).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
                OffsetDateTime offsetDateTime = OffsetDateTime.of(dataBaseDate, ZoneId.systemDefault().getRules().getOffset(dataBaseDate));

                user = new User(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("display_name")
                );
                user.setSignedUpAt(offsetDateTime);
            }
            Post post = new Post(
                       rs.getLong("post_id"),
                       rs.getLong("post_user_id"),
                       rs.getString("body"),
                       rs.getString("title"));
            LocalDateTime dataBaseDate = LocalDateTime.parse(rs.getObject("created_at", OffsetDateTime.class).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
            OffsetDateTime offsetDateTime = OffsetDateTime.of(dataBaseDate, ZoneId.systemDefault().getRules().getOffset(dataBaseDate));
            post.setCreatedAt(offsetDateTime);
            user.getPosts().put(post.getId(), post);

        }
        return user;
    }
}
