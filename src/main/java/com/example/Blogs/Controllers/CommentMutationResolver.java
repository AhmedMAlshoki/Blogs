package com.example.Blogs.Controllers;


import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CommentMutationResolver {
    @Autowired
    private final CommentService commentService;


    @Autowired
    public CommentMutationResolver(CommentService commentService) {
        this.commentService = commentService;
    }

    @MutationMapping
    public CommentDTO saveNewComment(@Argument Long postId,@Argument String body) {
        return commentService.saveNewComment(postId, body);
    }


    @MutationMapping
    public String updateComment(@Argument Long id,@Argument String body) {
        return commentService.updateComment(body, id);
    }

    @MutationMapping
    public String deleteComment(@Argument Long id) {
        return commentService.deleteComment(id);
    }

}
