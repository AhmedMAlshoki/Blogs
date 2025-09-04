package com.example.Blogs.AuthenticationObject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
public class AdvancedEmailPasswordToken extends UsernamePasswordAuthenticationToken {
    @Setter
    private String IP;
    private String timeZone;
    public AdvancedEmailPasswordToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AdvancedEmailPasswordToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public void setTimeZone(LocalDateTime timeZone) {

    }

}
