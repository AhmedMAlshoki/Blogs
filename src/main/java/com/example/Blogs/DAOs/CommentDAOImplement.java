package com.example.Blogs.DAOs;

import com.example.Blogs.Models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class CommentDAOImplement extends DAO_Implementaion implements CommentDAO {

    @Autowired
    public CommentDAOImplement(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Comment saveNewComment(Comment comment) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Comment updateComment(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> findByPost(Long postId) {
        return List.of();
    }

    @Override
    public List<Comment> findByUser(Long userId) {
        return List.of();
    }
}
