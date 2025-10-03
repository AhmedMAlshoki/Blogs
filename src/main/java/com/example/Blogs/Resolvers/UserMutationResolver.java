package com.example.Blogs.Resolvers;


import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.UserService;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
public class UserMutationResolver {

    private final UserService userService;

    @Autowired
    public UserMutationResolver(UserService userService) {
        this.userService = userService;
    }

    @Validated
    @MutationMapping
    public String registerNewUser(@Argument @NotBlank String username,
                                  @Argument @NotBlank String displayName,
                                  @Argument @NotBlank @Email String email,
                                  @Argument @NotBlank @Size(min = 8) String password) {
        return userService.registerUser(username, displayName, email, password);
    }

    @Validated
    @MutationMapping
    @PreAuthorize("@userService.isUserAuthorized(#id)")
    public UserDTO update(@Argument @Positive @NotNull Long id,
                          @Argument @NotBlank String username,
                          @Argument @NotBlank @Size(min = 8) String password,
                          @Argument @NotBlank @Email String email,
                          @Argument @NotBlank String displayName) {
        return userService.updateUser(id,username, password, email, displayName);
    }

    @Validated
    @MutationMapping
    @PreAuthorize("@userService.isUserAuthorized(#id)")
    public String deleteUser(@Argument @Positive @NotNull Long id) {
        return userService.deleteUser(id);
    }

    @Validated
    @MutationMapping
    public String followUser(@Argument @Positive @NotNull Long userId) {
        return userService.followUser(userId);
    }

    @Validated
    @MutationMapping
    public String unfollowUser(@Argument @Positive @NotNull Long userId) {
        return userService.unfollowUser(userId);
    }
}
