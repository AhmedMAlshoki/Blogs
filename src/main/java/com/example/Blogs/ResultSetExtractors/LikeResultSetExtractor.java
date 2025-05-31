package com.example.Blogs.ResultSetExtractors;

import com.example.Blogs.Models.Like;
import com.example.Blogs.Models.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LikeResultSetExtractor implements ResultSetExtractor<Map<Long, Post>> {
   Map<Long, Post> posts;

   public LikeResultSetExtractor(Map<Long, Post> posts) {
       this.posts = posts;
   }
    @Override
    public Map<Long, Post> extractData(ResultSet rs) throws SQLException, DataAccessException {

        while (rs.next()) {
            if (rs.getLong("l.like_id") == 0)
                continue;
            Like like = new Like(rs.getLong("like_user_id"));
            posts.get(rs.getLong("like_post_id")).getLikes().add(like);
        }
        return posts;
    }
}
