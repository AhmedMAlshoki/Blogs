package com.example.Blogs.Filters;

import com.example.Blogs.Filters.Wrappers.CachedBodyHttpServletRequest;
import com.example.Blogs.Utils.ApiUtils.ApiHelperMethods;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestCachingFilter implements Filter {

    private final ApiHelperMethods apiHelperMethods = new ApiHelperMethods();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Only wrap requests that we need to read the body from
        if (apiHelperMethods.isGraphQLRequest(httpRequest)) {
            CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest);
            chain.doFilter(wrappedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

}
