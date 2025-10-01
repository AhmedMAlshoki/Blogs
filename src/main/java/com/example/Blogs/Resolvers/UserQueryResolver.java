package com.example.Blogs.Resolvers;


import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UserQueryResolver {

    private final UserService userService;

    @Autowired
    public UserQueryResolver(UserService userService) {
        this.userService = userService;
    }



    @QueryMapping
    public UserDTO user(@Argument Long id) {
        return userService.findById(id);
    }

    @QueryMapping
    public UserDTO userByUsername(@Argument String username) {
        return userService.findByUsername(username);
    }

    @QueryMapping
    public Iterable<UserDTO> followers(@Argument Long id) {
        return userService.findFollowers(id);
    }

    @QueryMapping
    public Iterable<UserDTO> followingList(@Argument Long id) {
        return userService.findFollowings(id);
    }

    @QueryMapping
    public UserDTO userProfile(@Argument Long id) {
        return userService.getUserFullProfile(id);
    }
}
