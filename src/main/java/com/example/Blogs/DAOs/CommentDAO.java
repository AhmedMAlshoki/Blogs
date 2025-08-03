package com.example.Blogs.DAOs;

import com.example.Blogs.Models.Comment;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentDAO {
    Comment findById(Long id);
    Comment saveNewComment(Comment comment);

    String deleteById(Long id);
    boolean existsById(Long id);
    String updateComment(String body,Long id);
    List<Comment> findByPost(Long postId);
    List<Comment> findByUser(Long userId);
    Map<Long,List<Comment>> findByMultiplePosts(List<Long> postIds);
}
