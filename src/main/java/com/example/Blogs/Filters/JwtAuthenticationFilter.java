package com.example.Blogs.Filters;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.Exceptions.JwtFilterException;
import com.example.Blogs.Exceptions.UserNotAuthenticated;
import com.example.Blogs.Services.Security.UserDetailsImpl;
import com.example.Blogs.Services.Security.UserDetailsServiceImpl;
import com.example.Blogs.Utils.ApiUtils.ApiHelperMethods;
import com.example.Blogs.Utils.Jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtil jwtUtil;
    private ApiHelperMethods apiHelperMethods = new ApiHelperMethods();
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{
            String jwt = jwtUtil.parseJwt(request);
            if(jwt != null && jwtUtil.validateJwtToken(jwt)){
                String username = jwtUtil.getUserIdFromJwtToken(jwt);
                Long id = Long.parseLong(username);
                UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserById(id);
                AdvancedEmailPasswordToken authentication =
                        new AdvancedEmailPasswordToken(user, null, user.getAuthorities());
                authentication.setJwt(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (jwt == null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) {
                    throw new UserNotAuthenticated("User is not authenticated");
                }
                else if (authentication instanceof AdvancedEmailPasswordToken advancedToken) {
                    jwt = jwtUtil.generateJwtToken(advancedToken);
                    advancedToken.setJwt(jwt);
                }
            }
        }
        catch (JwtFilterException e){
            throw new JwtFilterException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
