package com.example.Blogs.Controllers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PostMutationResolver {

    @Autowired
    private GraphQLExceptionResolver graphQLExceptionResolver;
    private final PostService postService;

    @Autowired
    public PostMutationResolver(PostService postService) {
        this.postService = postService;
    }

    @MutationMapping
    public PostDTO saveNewPost(@Argument String body, @Argument String title) {
        return postService.savePost(body,title);
    }

    @MutationMapping
    public PostDTO updatePost(@Argument Long id,@Argument String body,@Argument String title) {
        return postService.updatePost(id,body,title);
    }

    @MutationMapping
    public String deletePost(@Argument Long id) {
        return postService.deletePost(id);
    }

    @MutationMapping
    public String saveNewLike(@Argument Long id) {
        return postService.likePost(id);
    }

    @MutationMapping
    public String deleteLike(@Argument Long id) {
        return postService.dislikePost(id);
    }
}
