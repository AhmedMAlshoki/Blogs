package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Services.PostService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
public class PostMutationResolver {

    private final PostService postService;

    @Autowired
    public PostMutationResolver(PostService postService) {
        this.postService = postService;
    }

    @Validated
    @MutationMapping
    public PostDTO saveNewPost(@Argument @NotBlank String body,
                               @Argument @Size(min = 1, max = 50) String title) {
        return postService.savePost(body,title);
    }

    @Validated
    @MutationMapping
    @PreAuthorize("@userService.isUserAuthorized(#id)")
    public PostDTO updatePost(@Argument @Positive @NotNull Long id,
                              @Argument @NotBlank String body,
                              @Argument @Size(min = 1, max = 50) String title) {
        return postService.updatePost(id,body,title);
    }

    @Validated
    @MutationMapping
    @PreAuthorize("@userService.isUserAuthorized(#id)")
    public String deletePost(@Argument @Positive @NotNull Long id) {
        return postService.deletePost(id);
    }

    @Validated
    @MutationMapping
    public String saveNewLike(@Argument @Positive @NotNull Long id) {
        return postService.likePost(id);
    }

    @Validated
    @MutationMapping
    public String deleteLike(@Argument @Positive @NotNull Long id) {
        return postService.dislikePost(id);
    }
}
