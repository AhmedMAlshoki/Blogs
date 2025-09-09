package com.example.Blogs.Filters;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.Exceptions.HandlingRequestException;
import com.example.Blogs.Utils.ApiUtils.ApiHelperMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.graphql.GraphQlRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Order(2)
public class EmailPasswordAuthenticationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
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

            if (apiHelperMethods.isLoginMutation(requestBody)) {
                try {
                    // Parse the GraphQL request to extract email and password
                    GraphQlRequest graphQLRequest = apiHelperMethods.parseGraphQLRequest(requestBody);
                    String email = apiHelperMethods.extractEmail(graphQLRequest);
                    String password = apiHelperMethods.extractPassword(graphQLRequest);

                    if (email != null && password != null) {
                        // Create authentication token
                        AdvancedEmailPasswordToken authToken =
                                new AdvancedEmailPasswordToken(email, password);

                        // Set authentication in security context
                        try {
                            Authentication authentication = authenticationManager.authenticate(authToken);
                            SecurityContextHolder.getContext().setAuthentication(authentication);

                            // Optionally, you can modify the response here or let GraphQL handle it
                            // For example, you might want to add a JWT token to the response

                        } catch (AuthenticationException e) {
                            // Authentication failed - you can handle this here or let GraphQL handle it
                            // Clear any existing authentication
                            SecurityContextHolder.clearContext();
                        }
                    }
                } catch (Exception e) {
                    // Log the error and continue with the filter chain
                    throw new HandlingRequestException("Failed to process login mutation");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
