package com.example.Blogs.AuthenticationProviders;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Services.Security.UserDetailsImpl;
import com.example.Blogs.Services.Security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private  PasswordEncoder passwordEncoder;



    public CustomAuthenticationProvider(){}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AdvancedEmailPasswordToken token = (AdvancedEmailPasswordToken) authentication;
        String email = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof String)
            email = (String) principal;
        else if (principal instanceof UserDetailsImpl)
            email = ((UserDetailsImpl) principal).getUsername();
        String password = (String) authentication.getCredentials();
        UserDetailsImpl user = userDetailsService.loadUserByUsername(email);
        String emailFromDB = user.getUsername();
        String passwordFromDB = user.getPassword();;
        if (emailFromDB.equals(email) && passwordEncoder.matches(password, passwordFromDB)) {
            log.info("Authentication successful, Validate email and password");
            List<GrantedAuthority> authorities = new ArrayList<>();
            GrantedAuthority authority = new SimpleGrantedAuthority("USER");
            authorities.add(authority);
            return new AdvancedEmailPasswordToken(user, password, authorities, null);
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AdvancedEmailPasswordToken.class.isAssignableFrom(authentication);
    }
}
