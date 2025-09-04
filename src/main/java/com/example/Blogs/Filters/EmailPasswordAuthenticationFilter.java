package com.example.Blogs.Filters;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.Exceptions.HandlingRequestException;
import com.example.Blogs.Filters.Wrappers.CachedBodyHttpServletRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.GraphQlRequest;
import org.springframework.graphql.support.DefaultGraphQlRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EmailPasswordAuthenticationFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Check if this is a GraphQL request
        if (isGraphQLRequest(request)) {
            // Read the request body to check for login mutation
            String requestBody = getRequestBody(request);

            if (isLoginMutation(requestBody)) {
                try {
                    // Parse the GraphQL request to extract email and password
                    GraphQlRequest graphQLRequest = parseGraphQLRequest(requestBody);
                    String email = extractEmail(graphQLRequest);
                    String password = extractPassword(graphQLRequest);

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

    private boolean isGraphQLRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        String requestURI = request.getRequestURI();

        return "POST".equals(request.getMethod()) &&
                (requestURI.contains("/graphql") || requestURI.contains("/api/graphql")) &&
                (contentType != null && contentType.contains("application/json"));
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        // Create a custom wrapper to read the body multiple times
        if (!(request instanceof CachedBodyHttpServletRequest cachedRequest)) {
            throw new IllegalArgumentException("Request must be wrapped with CachedBodyHttpServletRequest");
        }

        return new String(cachedRequest.getCachedBody(), StandardCharsets.UTF_8);
    }

    private boolean isLoginMutation(String requestBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            String query = jsonNode.path("query").asText();

            // Check if the query contains the login mutation
            // This is a simple check - you might want to make it more sophisticated
            return query.contains("mutation") &&
                    (query.contains("login(") || query.contains("login "));

        } catch (Exception e) {
            return false;
        }
    }

    private GraphQlRequest parseGraphQLRequest(String requestBody) throws IOException {
            JsonNode jsonNode = objectMapper.readTree(requestBody);

            String document = jsonNode.path("query").asText();
            Map<String, Object> variables =
                      objectMapper.convertValue(jsonNode.path("variables"),
                                       new TypeReference<Map<String, Object>>() {});
            String operationName = jsonNode.path("operationName").asText(null);

            return new DefaultGraphQlRequest(document, operationName, variables , null);
    }

    private String extractEmail(GraphQlRequest request) throws HandlingRequestException {
        try {
            Map<String, Object> variables = request.getVariables();
            return (String) variables.get("email");
        } catch (Exception e) {
            throw new HandlingRequestException("Failed to extract email from request");
        }
    }

    private String extractPassword(GraphQlRequest request) throws HandlingRequestException {
        try {
            Map<String, Object> variables = request.getVariables();
            return (String) variables.get("password");
        } catch (Exception e) {
            throw new HandlingRequestException("Failed to extract password from request");
        }
    }
}
