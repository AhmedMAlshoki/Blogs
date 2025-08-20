package com.example.Blogs.Controllers;

import com.example.Blogs.DTOs.CommentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
public class CommentQueryResolver {

    @QueryMapping
    public Iterable<CommentDTO> commentsByPost(Long id) {
        return null;
    }

    @QueryMapping
    public Iterable<CommentDTO> commentsByUser(Long id) {
        return null;
    }
}
