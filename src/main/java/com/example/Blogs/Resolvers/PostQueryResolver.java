package com.example.Blogs.Resolvers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Slf4j
@Controller
public class PostQueryResolver {
    private final PostService postService;

    @Autowired
    public PostQueryResolver(PostService postService) {
        this.postService = postService;
    }


    @QueryMapping
    public PostDTO  post(@Argument Long id) {
        return postService.getPost(id);
    }

    @QueryMapping
    public Iterable<PostDTO> postsByUser(@Argument Long userId) {
        return postService.getUserPosts(userId);
    }

    @QueryMapping
    public Iterable<PostDTO> postsByFollowing(@Argument Long userId) {
        return postService.getFollowingPosts(userId);
    }

    @QueryMapping
    public Iterable<PostDTO> postsBySearchQuery(@Argument String searchQuery, @Argument List<Long> authorFilter,@Argument String minDate,
                                                @Argument String maxDate, @Argument Integer size,@Argument Integer offset) {
        return postService.getPostsBySearchQuery(searchQuery, authorFilter, minDate, maxDate, size, offset);
    }

    @QueryMapping
    public Iterable<PostDTO> getTopPosts(@Argument Integer offset) {
        return postService.getTopPosts(offset);
    }
}
