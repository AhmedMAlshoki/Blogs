package com.example.Blogs.DAOs;

import com.example.Blogs.Models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
    Comment findById(Long id);
    Comment saveNewComment(Comment comment);
    void deleteById(Long id);
    boolean existsById(Long id);
    Comment updateComment(Comment comment);
    List<Comment> findByPost(Long postId);
    List<Comment> findByUser(Long userId);
}
