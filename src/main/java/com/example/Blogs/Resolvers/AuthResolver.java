package com.example.Blogs.Resolvers;


import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.CustomResponses.LoginResponse;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class AuthResolver {

    private final UserService userService;

    @Autowired
    public AuthResolver(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    private LoginResponse login(@Argument String email, @Argument String password) {
            return  userService.loginResponse();

    }


}
