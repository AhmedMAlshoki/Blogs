package com.example.Blogs.Services;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.DAOs.CommentDAO;
import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Mappers.MapStructMappers.CommentMapper;
import com.example.Blogs.Models.Comment;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentDAO commentDAO;
    private final CommentMapper commentMapper;
    @Setter
    private  AdvancedEmailPasswordToken advancedEmailPasswordToken;

    @Autowired
    public CommentService(CommentDAO commentDAO, CommentMapper commentMapper, AdvancedEmailPasswordToken advancedEmailPasswordToken) {
        this.commentDAO = commentDAO;
        this.commentMapper = commentMapper;
        this.advancedEmailPasswordToken = (AdvancedEmailPasswordToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isUserAuthorized(Long id) {
        return Objects.equals(advancedEmailPasswordToken.getCurrentUserId(), findCommentOwner(id));
    }

    private Long findCommentOwner(Long id) {
        return commentDAO.findCommentOwner(id);

    }

    public CommentDTO getComment(Long id) {
        return commentMapper.commentToCommentDTO(commentDAO.findById(id));
    }


    public Iterable<CommentDTO> getPostComments(Long id) {
        List<Comment> comments = commentDAO.findByPost(id);
        return comments.stream().map(commentMapper::commentToCommentDTO).toList();
    }

    public Iterable<CommentDTO> getUserComments(Long id) {
        List<Comment> comments = commentDAO.findByUser(id);
        return comments.stream().map(commentMapper::commentToCommentDTO).toList();
    }

    public Map<Long,List<CommentDTO>> getMultiplePostComments(Long[] id) {
        List<Long> postIds = Arrays.stream(id).toList();
        Map<Long,List<Comment>> comments = commentDAO.findByMultiplePosts(postIds);
        return comments.entrySet().stream().
                collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().
                                map(commentMapper::commentToCommentDTO).toList()));
    }

    public CommentDTO saveNewComment(Long postId, String body) {
        Long userId = advancedEmailPasswordToken.getCurrentUserId();
        Timezone timezone = advancedEmailPasswordToken.getClientApiInfo().getTimezone();
        Comment comment = new Comment(body, postId, userId);
        return commentMapper.commentToCommentDTO(commentDAO.saveNewComment(comment,timezone));
    }

    public String updateComment(String body, Long id) {
        Timezone timezone = advancedEmailPasswordToken.getClientApiInfo().getTimezone();
        return commentDAO.updateComment(body,id,timezone);
    }

    public String deleteComment(Long id) {
        return commentDAO.deleteById(id);
    }
}
