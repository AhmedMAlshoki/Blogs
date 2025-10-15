package com.example.Blogs.Services.Security;

import com.example.Blogs.Models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String email;
    private String password;


    public UserDetailsImpl(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public User getUser(){
        return new User(this.id, this.email, this.password);
    }


    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user.getId(), user.getEmail(), user.getPassword());
    }

}
