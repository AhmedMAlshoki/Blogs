package com.example.Blogs.DAOs;

import com.example.Blogs.DAOs.DAOUtilities.DAOUtilities;
import com.example.Blogs.DAOs.SqlQueries.CommentQueries;
import com.example.Blogs.Exceptions.CommentNotFoundException;
import com.example.Blogs.Exceptions.PostNotFoundException;
import com.example.Blogs.Models.Comment;
import com.example.Blogs.ResultSetExtractors.CommentResultSetExtractor;
import com.example.Blogs.RowMappers.CommentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommentDAOImplement extends DAO_Implementaion implements CommentDAO {

    private  DAOUtilities daoUtilities;
    private CommentQueries commentQueries;
    private UserDAO userDAO;
    private PostDAO postDAO;
    @Autowired
    public CommentDAOImplement(JdbcTemplate jdbcTemplate , DAOUtilities daoUtilities,
                               CommentQueries commentQueries, UserDAO userDAO, PostDAO postDAO) {
        super(jdbcTemplate);
        this.daoUtilities = daoUtilities;
        this.commentQueries = commentQueries;
        this.userDAO = userDAO;
        this.postDAO = postDAO;
    }

    @Override
    public Comment findById(Long id) throws CommentNotFoundException {
        String sql = commentQueries.findById();
         if (existsById(id)) {
             Map<Long , List<Comment>> comments = jdbcTemplate.query(sql, new CommentResultSetExtractor(), id);
             if (comments == null)
                 return null;
             else
                 return comments.get(id).getFirst();
         } else {
             throw new CommentNotFoundException("Comment not found");
         }
    }

    @Override
    public Comment saveNewComment(Comment comment) {
        String sql = commentQueries.insertQuery();
        return jdbcTemplate.queryForObject(sql, new CommentRowMapper(),
                                                 comment.getBody(),
                                                 comment.getUserId(),
                                                 comment.getPostId());
    }

    @Override
    public String deleteById(Long id) {
        String sql = commentQueries.deleteQuery();
        int delete = jdbcTemplate.update(sql, id);
        if (delete == 1) {
            return "Comment deleted successfully.";
        } else {
            return "Comment delete failed.";
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = commentQueries.existsById();
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(sql, Boolean.class, id)
        );
    }

    @Override
    public String updateComment(String body, Long id) {
        String sql = commentQueries.updateQuery();
        int update = jdbcTemplate.update(sql, body, id);
        if (update == 1) {
            return "Comment updated successfully.";
        } else {
            return "Comment update failed.";
        }
    }

    @Override
    public List<Comment> findByPost(Long postId) throws PostNotFoundException {
        if (postDAO.existsById(postId)) {
            String sql = commentQueries.SQLQueryForFindByPost();
            Map<Long,List<Comment>> comments = jdbcTemplate.query(sql, new CommentResultSetExtractor(), postId);
            if (comments == null)
                return List.of();
            else
                return comments.get(postId);
        }
        else
            throw new PostNotFoundException("Post not found.");
    }

    @Override
    public List<Comment> findByUser(Long userId) throws PostNotFoundException{
        if (userDAO.existsById(userId)) {
            String sql = commentQueries.SQLQueryForFindByUser();
            Map<Long,List<Comment>> comments = jdbcTemplate.query(sql, new CommentResultSetExtractor(), userId);
            if (comments == null)
                return List.of();
            else
                return comments.values().stream().flatMap(List::stream).toList();
        }
        else
            throw new PostNotFoundException("User not found.");
    }

    @Override
    public Map<Long, List<Comment>> findByMultiplePosts(List<Long> postIds)  {
        String sql = commentQueries.SQLQueryForFindByMultiplePosts();
        Object[] params = daoUtilities.preparingParamForTheQuery(postIds);
        return jdbcTemplate.query(sql, new CommentResultSetExtractor(), params);
    }
}
