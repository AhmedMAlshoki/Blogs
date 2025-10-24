package com.example.Blogs.Config;


import com.example.Blogs.DataLoaders.CommentDataLoader;
import com.example.Blogs.DataLoaders.PostDataLoader;
import com.example.Blogs.DataLoaders.UserDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class DataLoaderConfig {
    @Component
    public static class DataLoaderInterceptor implements WebGraphQlInterceptor {

        private final UserDataLoader userDataLoader;
        private final PostDataLoader postDataLoader;
        private final CommentDataLoader commentDataLoader;

        public DataLoaderInterceptor(UserDataLoader userDataLoader,
                                     PostDataLoader postDataLoader,
                                     CommentDataLoader commentDataLoader) {
            this.userDataLoader = userDataLoader;
            this.postDataLoader = postDataLoader;
            this.commentDataLoader = commentDataLoader;
            log.info("=== DataLoaderInterceptor constructor called ===");
        }

        @Override
        public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
            log.info("=== INTERCEPTOR CALLED - Creating DataLoaderRegistry ===");
            DataLoaderRegistry registry = new DataLoaderRegistry();
            registry.register("userDataLoader", userDataLoader.userDataLoader());
            registry.register("postDataLoader", postDataLoader.postDataLoader());
            registry.register("commentDataLoader", commentDataLoader.commentDataLoader());

            log.info("=== Registered DataLoaders: {} ===", registry.getKeys());

            // Configure the ExecutionInput to use this registry
            request.configureExecutionInput((executionInput, builder) -> {
                log.info("=== Configuring ExecutionInput with DataLoaderRegistry ===");
                return builder.dataLoaderRegistry(registry).build();
            });

            return chain.next(request);
        }
    }

}
