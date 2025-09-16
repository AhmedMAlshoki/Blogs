package com.example.Blogs.Services.Security;

import com.example.Blogs.DAOs.UserDAO;
import com.example.Blogs.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userDAO.getUserCredential(email);

        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
        return UserDetailsImpl.build(user);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

        Optional<User> userOptional = userDAO.findById(id);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return UserDetailsImpl.build(userOptional.get());
    }
}
