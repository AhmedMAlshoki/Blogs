package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Services.PostService;
import jakarta.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Controller
public class PostQueryResolver {
    private final PostService postService;

    @Autowired
    public PostQueryResolver(PostService postService) {
        this.postService = postService;
    }


    @Validated
    @QueryMapping
    public PostDTO  post(@Argument @Positive @NotNull Long id) {
        return postService.getPost(id);
    }

    @Validated
    @QueryMapping
    public Iterable<PostDTO> postsByUser(@Argument @Positive @NotNull Long userId) {
        return postService.getUserPosts(userId);
    }

    @Validated
    @QueryMapping
    public Iterable<PostDTO> postsByFollowing(@Argument @Positive @NotNull Long userId) {
        return postService.getFollowingPosts(userId);
    }

    @Validated
    @QueryMapping
    public Iterable<PostDTO> postsBySearchQuery(@Argument @NotBlank @Size(min = 1, max = 100) String searchQuery,
                                                @Argument @Size(min = 0, max = 5) List<Long> authorFilter,
                                                @Argument OffsetDateTime minDate,
                                                @Argument OffsetDateTime maxDate,
                                                @Argument @Min(0) Integer size,
                                                @Argument @Max(50) Integer offset) {
        return postService.getPostsBySearchQuery(searchQuery, authorFilter, minDate, maxDate, size, offset);
    }

    @Validated
    @QueryMapping
    public Iterable<PostDTO> getTopPosts(@Argument @Min(1) @Max(100) Integer offset) {
        return postService.getTopPosts(offset);
    }
}
