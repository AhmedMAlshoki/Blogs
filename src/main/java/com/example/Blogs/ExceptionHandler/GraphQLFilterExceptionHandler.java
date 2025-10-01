package com.example.Blogs.ExceptionHandler;
import com.example.Blogs.Exceptions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class GraphQLFilterExceptionHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler({
            UserNotFoundException.class,
            PostNotFoundException.class,
            CommentNotFoundException.class,
            ExictingUserException.class,
            HandlingRequestException.class,
            UserNotAuthenticated.class,
            AuthenticationException.class,
            JwtFilterException.class,
            TooManyRequestsException.class
    })
    public ResponseEntity<Map<String, Object>> handleFilterExceptions(Exception ex) {
        GraphQLError error = switch (ex) {
            case UserNotFoundException unf -> prepareGraphQLErrorResponse(
                    unf.getMessage(), ErrorType.NOT_FOUND, 404);
            case PostNotFoundException pf -> prepareGraphQLErrorResponse(
                    pf.getMessage(), ErrorType.NOT_FOUND, 404);
            case CommentNotFoundException cnf -> prepareGraphQLErrorResponse(
                    cnf.getMessage(), ErrorType.NOT_FOUND, 404);
            case ExictingUserException eue -> prepareGraphQLErrorResponse(
                    eue.getMessage(), ErrorType.BAD_REQUEST, 400);
            case HandlingRequestException hre -> prepareGraphQLErrorResponse(
                    hre.getMessage(), ErrorType.BAD_REQUEST, 400);
            case UserNotAuthenticated una -> prepareGraphQLErrorResponse(
                    una.getMessage(), ErrorType.UNAUTHORIZED, 401);
            case AuthenticationException authEx -> prepareGraphQLErrorResponse(
                    authEx.getMessage(), ErrorType.UNAUTHORIZED, 401);
            case JwtFilterException jwtEx -> prepareGraphQLErrorResponse(
                    jwtEx.getMessage(), ErrorType.UNAUTHORIZED, 401);
            case TooManyRequestsException tmrEx -> prepareGraphQLErrorResponse(
                    tmrEx.getMessage(), ErrorType.BAD_REQUEST, 429);
            default -> prepareGraphQLErrorResponse(
                    "Internal server error", ErrorType.INTERNAL_ERROR, 500);
        };

        Map<String, Object> errorResponse = Map.of(
                "data", (Object) null,
                "errors", Collections.singletonList(error.toSpecification())
        );

        return ResponseEntity
                .status((HttpStatusCode) error.getExtensions().get(" statusCode"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    private GraphQLError prepareGraphQLErrorResponse(String message, ErrorType errorType, int statusCode) {
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(message)
                .extensions(Map.of("statusCode", statusCode))
                .build();
    }
}
