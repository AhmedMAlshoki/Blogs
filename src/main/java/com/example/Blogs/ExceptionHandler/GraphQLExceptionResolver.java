package com.example.Blogs.ExceptionHandler;


import com.example.Blogs.Exceptions.*;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GraphQLExceptionResolver extends DataFetcherExceptionResolverAdapter {


    @Override
    public GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        return switch (ex) {
            case UserNotFoundException unf -> prepareGraphQLErrorResponse(
                    unf.getMessage(), ErrorType.NOT_FOUND, env, Map.of("statusCode", 404));
            case PostNotFoundException pf -> prepareGraphQLErrorResponse(
                    pf.getMessage(), ErrorType.NOT_FOUND, env, Map.of("statusCode", 404));
            case CommentNotFoundException cnf -> prepareGraphQLErrorResponse(
                    cnf.getMessage(), ErrorType.NOT_FOUND, env, Map.of("statusCode", 404));
            case ExictingUserException eue -> prepareGraphQLErrorResponse(
                    eue.getMessage(), ErrorType.BAD_REQUEST, env, Map.of("statusCode", 400));
            case HandlingRequestException hre -> prepareGraphQLErrorResponse(
                    hre.getMessage(), ErrorType.BAD_REQUEST, env, Map.of("statusCode", 400));
            default ->
                    prepareGraphQLErrorResponse(
                            "Internal server error",
                            ErrorType.INTERNAL_ERROR,
                            env,
                            Map.of("statusCode", HttpStatusCode.valueOf(500)));
        };

    }
    private GraphQLError prepareGraphQLErrorResponse(
            String message,
            ErrorType errorType,
            DataFetchingEnvironment env,
            Map<String, Object> extensions) {
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(message)
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .extensions(extensions)
                .build();
    }
}
