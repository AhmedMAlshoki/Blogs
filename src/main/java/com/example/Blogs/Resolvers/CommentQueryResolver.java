package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
public class CommentQueryResolver {

    @Autowired
    private CommentService commentService;
    @QueryMapping
    public Iterable<CommentDTO> commentsByPost(Long id) {
        return commentService.getPostComments(id);
    }

    @QueryMapping
    public Iterable<CommentDTO> commentsByUser(Long id) {
        return commentService.getUserComments(id);
    }

    @QueryMapping
    public CommentDTO comment(Long id) {
        return commentService.getComment(id);
    }
}
