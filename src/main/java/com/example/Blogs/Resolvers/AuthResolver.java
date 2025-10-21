package com.example.Blogs.Resolvers;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.CustomResponses.LoginResponse;
import com.example.Blogs.Services.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@Slf4j
public class AuthResolver {
    private final UserService userService;

    @Autowired
    public AuthResolver(UserService userService) {
        this.userService = userService;
    }

    @Validated
    @MutationMapping
    public LoginResponse login(@Argument @NotNull  @Email String email,
                                @Argument @NotNull String password) {
        AdvancedEmailPasswordToken advancedEmailPasswordToken =
                (AdvancedEmailPasswordToken) SecurityContextHolder.getContext().getAuthentication();
        log.info("Login request detected , Auth Resolver with token : {}",advancedEmailPasswordToken.getJwt());
        return  userService.loginResponse();

    }


}
