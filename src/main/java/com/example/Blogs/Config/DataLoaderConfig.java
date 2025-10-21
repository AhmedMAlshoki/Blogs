package com.example.Blogs.Config;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Models.Post;
import com.example.Blogs.Services.CommentService;
import com.example.Blogs.Services.PostService;
import com.example.Blogs.Services.UserService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Configuration
public class DataLoaderConfig {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;


    @Bean
    public DataLoader<Long, UserDTO> userDataLoader() {
        return DataLoaderFactory.newDataLoader((userIds -> {
            List<UserDTO> users = userService.findByIds(userIds);
            return CompletableFuture.completedFuture(users);
        }));
    }

    @Bean
    public DataLoader<Long, PostDTO> postDataLoader() {
        return DataLoaderFactory.newDataLoader((userIds -> {
            List<PostDTO> posts = postService.findByUserIds(userIds);
            return CompletableFuture.completedFuture(posts);
        }));
    }

    @Bean
    public DataLoader<Long, CommentDTO> commentDataLoader() {
        return DataLoaderFactory.newDataLoader((postIds -> {
            List<CommentDTO> comments = commentService.findByMultiplePosts(postIds);
            return CompletableFuture.completedFuture(comments);
        }));
    }


}
