package com.example.Blogs.Controllers;

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
    @Autowired
    private GraphQLExceptionResolver graphQLExceptionResolver;
    @QueryMapping
    public Iterable<CommentDTO> commentsByPost(Long id) {
        return null;
    }

    @QueryMapping
    public Iterable<CommentDTO> commentsByUser(Long id) {
        return null;
    }
}
