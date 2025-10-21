package com.example.Blogs.Mappers.ResultSetExtractors;

import com.example.Blogs.Models.Comment;
import com.example.Blogs.Models.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentResultSetExtractor implements ResultSetExtractor<HashMap<Long, List<Comment>>> {
    HashMap<Long, List<Comment>> organizedComments = new HashMap<>();
    @Override
    public HashMap<Long, List<Comment>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()) {
            Comment comment = new Comment(
                    rs.getLong("id"),
                    rs.getString("body"),
                    rs.getLong("post_id"),
                    rs.getLong("user_id"),
                    rs.getObject("created_at", OffsetDateTime.class));
            if (!organizedComments.containsKey(rs.getLong("post_id")))
                organizedComments.put(rs.getLong("post_id"), new ArrayList<Comment>());
            organizedComments.get(rs.getLong("post_id")).add(comment);
        }
        return organizedComments;
    }
}
