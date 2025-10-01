package com.example.Blogs.Aspects;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.Services.CommentService;
import com.example.Blogs.Services.PostService;
import com.example.Blogs.Services.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
public class UserIdSetterAspect {
    @Pointcut("execution(* com.example.Blogs.Services.*.*(..))")
    public void serviceCall(){}
    @Before("serviceCall()")
    public void setAuthenticationToken(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        AdvancedEmailPasswordToken authentication = (AdvancedEmailPasswordToken)SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (target instanceof CommentService) {
                ((CommentService) target).setAdvancedEmailPasswordToken( authentication);
            } else if (target instanceof PostService) {
                ((PostService) target).setAdvancedEmailPasswordToken( authentication);
            } else if (target instanceof UserService) {
                ((UserService) target).setAdvancedEmailPasswordToken( authentication);
            }
        }
    }


}
