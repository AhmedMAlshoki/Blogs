package com.example.Blogs.Controllers;


import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.ExceptionHandler.GraphQLExceptionResolver;
import com.example.Blogs.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserMutationResolver {

    private final UserService userService;
    @Autowired
    private GraphQLExceptionResolver graphQLExceptionResolver;

    @Autowired
    public UserMutationResolver(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public String registerNewUser(String username, String displayName, String email, String password) {
        return userService.registerUser(username, displayName, email, password);
    }

    @MutationMapping
    public UserDTO update(String username, String password, String email, String displayName) {
        return userService.updateUser(username, password, email, displayName);
    }

    @MutationMapping
    public String deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    @MutationMapping
    public String followUser(Long userId) {
        return userService.followUser(userId);
    }

    @MutationMapping
    public String unfollowUser(Long userId) {
        return userService.unfollowUser(userId);
    }
}
