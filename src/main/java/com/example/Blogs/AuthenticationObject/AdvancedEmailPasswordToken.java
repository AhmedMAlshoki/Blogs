package com.example.Blogs.AuthenticationObject;

import com.example.Blogs.Utils.ApiUtils.ClientApiInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Getter
public class AdvancedEmailPasswordToken extends UsernamePasswordAuthenticationToken {
    @Setter
    ClientApiInfo clientApiInfo;
    @Setter
    String jwt;
    public AdvancedEmailPasswordToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AdvancedEmailPasswordToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public AdvancedEmailPasswordToken(Object principal, Object credentials, ClientApiInfo clientApiInfo) {
        super(principal, credentials);
        this.clientApiInfo = clientApiInfo;
    }

    public AdvancedEmailPasswordToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, ClientApiInfo clientApiInfo) {
        super(principal, credentials, authorities);
        this.clientApiInfo = clientApiInfo;
    }


}
