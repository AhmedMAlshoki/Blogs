package com.example.Blogs.ResultSetExtractors;

import com.example.Blogs.Models.Comment;
import com.example.Blogs.Models.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentResultSetExtractor implements ResultSetExtractor<Map<Long, List<Comment>>> {
    Map<Long, List<Comment>> organizedComments = Map.of();
    @Override
    public Map<Long, List<Comment>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()) {
            Comment comment = new Comment(
                    rs.getLong("id"),
                    rs.getString("body"),
                    rs.getLong("comment_post_id"),
                    rs.getLong("comment_user_id"),
                    rs.getObject("created_at", OffsetDateTime.class));
            if (!organizedComments.containsKey(rs.getLong("comment_post_id")))
                organizedComments.put(rs.getLong("comment_post_id"), new ArrayList<Comment>());
            organizedComments.get(rs.getLong("comment_post_id")).add(comment);
        }
        return organizedComments;
    }
}
