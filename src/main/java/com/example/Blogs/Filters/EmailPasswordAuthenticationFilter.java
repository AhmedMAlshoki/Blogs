package com.example.Blogs.Filters;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.Exceptions.HandlingRequestException;
import com.example.Blogs.Exceptions.UserNotFoundException;
import com.example.Blogs.Models.User;
import com.example.Blogs.Services.Security.UserDetailsImpl;
import com.example.Blogs.Utils.ApiUtils.ApiHelperMethods;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.GraphQlRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


public class EmailPasswordAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager;
    private final ApiHelperMethods apiHelperMethods = new ApiHelperMethods();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Check if this is a GraphQL request
        if (apiHelperMethods.isGraphQLRequest(request)) {
            // Read the request body to check for login mutation
            String requestBody = apiHelperMethods.getRequestBody(request);

            if (apiHelperMethods.isRegisterRequest(requestBody))
            {
                filterChain.doFilter(request,response);
            }
            if (apiHelperMethods.isLoginMutation(requestBody)) {
                try {
                    // Parse the GraphQL request to extract email and password
                    GraphQlRequest graphQLRequest = apiHelperMethods.parseGraphQLRequest(requestBody);
                    String email = apiHelperMethods.extractEmail(graphQLRequest);
                    String password = apiHelperMethods.extractPassword(graphQLRequest);

                    if (email != null && password != null) {
                        UserDetailsImpl user = new UserDetailsImpl(email, password);
                        // Create authentication token
                        AdvancedEmailPasswordToken authToken =
                                new AdvancedEmailPasswordToken(user, password);

                        // Set authentication in security context
                        try {
                            Authentication authentication = authenticationManager.authenticate(authToken);
                            SecurityContextHolder.getContext().setAuthentication(authentication);

                        } catch (AuthenticationException e) {
                            throw new UserNotFoundException("User not found");
                        }
                    }
                } catch (Exception e) {
                    throw new HandlingRequestException("Failed to process login mutation");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
