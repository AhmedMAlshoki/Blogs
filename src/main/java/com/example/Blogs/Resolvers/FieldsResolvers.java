package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.DTOs.UserDTO;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@Slf4j
public class FieldsResolvers {

    @SchemaMapping(typeName = "User", field = "posts")
    public CompletableFuture<List<PostDTO>> getPosts(UserDTO user, DataFetchingEnvironment env) {
        log.info("Field Resolver getPosts");
        log.info("Data Loader Registry {}",env.getDataLoaderRegistry()==null);
        log.info("Data Loader Registry Keys {}",env.getDataLoaderRegistry().getKeys());
        DataLoader<Long, List<PostDTO>> dataLoader = env.getDataLoader("postDataLoader");
        assert dataLoader != null;
        return dataLoader.load(user.getId());
    }

    @SchemaMapping(typeName = "Post", field = "user")
    public CompletableFuture<UserDTO> getUser(PostDTO post, DataFetchingEnvironment env) {
        log.info("Field Resolver getUser");
        DataLoader<Long, UserDTO> dataLoader = env.getDataLoader("userDataLoader");
        assert dataLoader != null;
        return dataLoader.load(post.getUserId());
    }

    @SchemaMapping(typeName = "Post", field = "comments")
    public CompletableFuture<List<CommentDTO>> getComments(PostDTO post, DataFetchingEnvironment env) {
        log.info("Field Resolver getComments");
        DataLoader<Long, List<CommentDTO>> dataLoader = env.getDataLoader("commentDataLoader");
        assert dataLoader != null;
        return dataLoader.load(post.getId());
    }



}
