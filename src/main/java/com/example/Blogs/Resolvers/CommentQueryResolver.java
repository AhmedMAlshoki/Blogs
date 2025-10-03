package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Services.CommentService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Controller
public class CommentQueryResolver {

    @Autowired
    private CommentService commentService;


    @Validated
    @QueryMapping
    public Iterable<CommentDTO> commentsByPost(@Argument @Positive @NotNull Long id) {
        return commentService.getPostComments(id);
    }

    @Validated
    @QueryMapping
    public Iterable<CommentDTO> commentsByUser(@Argument @Positive @NotNull Long id) {
        return commentService.getUserComments(id);
    }

    @Validated
    @QueryMapping
    public CommentDTO comment(@Argument @Positive @NotNull Long id) {
        return commentService.getComment(id);
    }
}
