package com.example.Blogs.Filters;

import com.example.Blogs.Filters.Wrappers.CachedBodyHttpServletRequest;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestCachingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Only wrap requests that we need to read the body from
        if (isGraphQLRequest(httpRequest)) {
            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
            chain.doFilter(wrappedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isGraphQLRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        String requestURI = request.getRequestURI();

        return "POST".equals(request.getMethod()) &&
                (requestURI.contains("/graphql") || requestURI.contains("/api/graphql")) &&
                (contentType != null && contentType.contains("application/json"));
    }
}
