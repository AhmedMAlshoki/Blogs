package com.example.Blogs.Controllers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.ExceptionHandler.GraphQLExceptionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Slf4j
@Controller
public class PostQueryResolver {

    @Autowired
    private GraphQLExceptionResolver graphQLExceptionResolver;

    @QueryMapping
    public PostDTO  post(@Argument Long id) {
        return null;
    }

    @QueryMapping
    public Iterable<PostDTO> postsByUser(@Argument Long userId) {
        return null;
    }

    @QueryMapping
    public Iterable<PostDTO> postsByFollowing(@Argument Long userId) {
        return null;
    }

    @QueryMapping
    public Iterable<PostDTO> postsBySearchQuery(@Argument String searchQuery, @Argument List<Long> authorFilter,@Argument String minDate,
                                                @Argument String maxDate, @Argument Integer size,@Argument Integer offset) {
        return null;
    }
}
