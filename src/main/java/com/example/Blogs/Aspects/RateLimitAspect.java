package com.example.Blogs.Aspects;

import com.example.Blogs.Exceptions.TooManyRequestsException;
import com.example.Blogs.Utils.FixedWindowRateLimiter;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimitAspect {
    private final FixedWindowRateLimiter rateLimiter ;

    @Autowired
    public RateLimitAspect(FixedWindowRateLimiter rateLimiter){
        this.rateLimiter = rateLimiter;
    }

    @Pointcut("execution(* com.example.Blogs.Resolvers.*.*(..))")
    public void resolverCall(){}

    @Before("resolverCall()")
    public void rateLimitCheck() throws TooManyRequestsException {
        if (!rateLimiter.allowRequest()) {
            throw new TooManyRequestsException("The site is busy, Please try again later");
        }
    }
}
