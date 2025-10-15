package com.example.Blogs.Filters;

import com.example.Blogs.Filters.Wrappers.CachedBodyHttpServletRequest;
import com.example.Blogs.Utils.ApiUtils.ApiHelperMethods;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
@Slf4j
public class RequestCachingFilter implements Filter {

    private final ApiHelperMethods apiHelperMethods = new ApiHelperMethods();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Only wrap requests that we need to read the body from
        if (apiHelperMethods.isGraphQLRequest(httpRequest)) {
            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
            log.info("GRAPHQL REQUEST HAS BEEN WRAPPED");
            chain.doFilter(wrappedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

}
