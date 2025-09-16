package com.example.Blogs.AuthenticationProviders;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.DTOs.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    private  PasswordEncoder passwordEncoder;



    public CustomAuthenticationProvider(){}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AdvancedEmailPasswordToken token = (AdvancedEmailPasswordToken) authentication;

        String email = token.getPrincipal().toString();
        String password = (String) token.getCredentials();
        String emailFromDB = userDetailsService.loadUserByUsername(email).getUsername();
        String passwordFromDB = userDetailsService.loadUserByUsername(email).getPassword();
        if (emailFromDB.equals(email) && passwordEncoder.matches(password, passwordFromDB)) {
            // If authentication is successful, create a new AdvancedEmailPasswordToken
            // marked as authenticated, and include authorities.
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(null);


            return new AdvancedEmailPasswordToken(email, password, authorities, null);
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AdvancedEmailPasswordToken.class.isAssignableFrom(authentication);
    }
}
