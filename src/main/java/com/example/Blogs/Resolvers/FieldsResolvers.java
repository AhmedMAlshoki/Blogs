package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.CommentService;
import com.example.Blogs.Services.PostService;
import com.example.Blogs.Services.UserService;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CompletableFuture;

@Controller
public class FieldsResolvers {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    @SchemaMapping(typeName = "Post", field = "user")
    public CompletableFuture<UserDTO> getUser(PostDTO post, DataLoader<Long, UserDTO> userDataLoader) {
        return userDataLoader.load(post.getUserId());
    }

    @SchemaMapping(typeName = "User", field = "posts")
    public Iterable<PostDTO> getPosts(UserDTO user) {
        return postService.getUserPosts(user.getId());
    }

    @SchemaMapping(typeName = "Post", field = "comments")
    public Iterable<CommentDTO> getComments(PostDTO post) {
        return commentService.getPostComments(post.getId());
    }

}
