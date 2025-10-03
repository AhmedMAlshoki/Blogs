package com.example.Blogs.Resolvers;


import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.UserService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Controller
public class UserQueryResolver {

    private final UserService userService;

    @Autowired
    public UserQueryResolver(UserService userService) {
        this.userService = userService;
    }



    @Validated
    @QueryMapping
    public UserDTO user(@Argument @Positive @NotNull Long id) {
        return userService.findById(id);
    }

    @Validated
    @QueryMapping
    public UserDTO userByUsername(@Argument @Positive @NotNull String username) {
        return userService.findByUsername(username);
    }

    @Validated
    @QueryMapping
    public Iterable<UserDTO> followers(@Argument @Positive @NotNull Long id) {
        return userService.findFollowers(id);
    }

    @Validated
    @QueryMapping
    public Iterable<UserDTO> followingList(@Argument @Positive @NotNull Long id) {
        return userService.findFollowings(id);
    }

    @Validated
    @QueryMapping
    public UserDTO userProfile(@Argument @Positive @NotNull Long id) {
        return userService.getUserFullProfile(id);
    }
}
