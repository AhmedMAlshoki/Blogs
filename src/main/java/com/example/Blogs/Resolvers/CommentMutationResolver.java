package com.example.Blogs.Resolvers;


import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Services.CommentService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
public class CommentMutationResolver {

    private final CommentService commentService;


    @Autowired
    public CommentMutationResolver(CommentService commentService) {
        this.commentService = commentService;
    }

    @Validated
    @MutationMapping
    public CommentDTO saveNewComment(@Argument @Positive @NotNull Long postId,
                                     @Argument @NotBlank String body) {
        return commentService.saveNewComment(postId, body);
    }


    @Validated
    @MutationMapping
    public String updateComment(@Argument @NotNull  @Positive Long id,
                                @Argument @NotBlank String body) {
        return commentService.updateComment(body, id);
    }

    @Validated
    @MutationMapping
    public String deleteComment(@Argument @NotNull @Positive Long id) {
        return commentService.deleteComment(id);
    }

}
