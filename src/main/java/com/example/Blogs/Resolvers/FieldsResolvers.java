package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Models.Post;
import com.example.Blogs.Services.CommentService;
import com.example.Blogs.Services.PostService;
import com.example.Blogs.Services.UserService;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.CompletableFuture;

@Controller
public class FieldsResolvers {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    @Validated
    @SchemaMapping(typeName = "Post", field = "user")
    public CompletableFuture<UserDTO> getUser(PostDTO post, DataLoader<Long, UserDTO> userDataLoader) {
        return userDataLoader.load(post.getUserId());
    }

    @Validated
    @SchemaMapping(typeName = "User", field = "posts")
    public CompletableFuture<PostDTO> getPosts(PostDTO post, DataLoader<Long, PostDTO> postDTODataLoader) {
        return postDTODataLoader.load(post.getId());
    } //DataLoader

    @Validated
    @SchemaMapping(typeName = "Post", field = "comments")
    public Iterable<CommentDTO> getComments(PostDTO post) {
        return commentService.getPostComments(post.getId());
    } //DataLoader



}
