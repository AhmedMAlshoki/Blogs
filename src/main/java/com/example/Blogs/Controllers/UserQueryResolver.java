package com.example.Blogs.Controllers;


import com.example.Blogs.DTOs.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UserQueryResolver {

    //UserService
    @Autowired
    private GraphQLExceptionResolver graphQLExceptionResolver;

    @QueryMapping
    public Iterable<UserDTO> users() {
        return null;
    }


    @QueryMapping
    public UserDTO user(@Argument Long id) {
        return null;
    }

    @QueryMapping
    public Iterable<UserDTO> followers(@Argument Long id) {
        return null;
    }

    @QueryMapping
    public Iterable<UserDTO> followingList(@Argument Long id) {
        return null;
    }

    @QueryMapping
    public Iterable<UserDTO> userProfile(@Argument Long id) {
        return null;
    }
}
