package com.example.Blogs.Services.Security;

import com.example.Blogs.DAOs.UserDAO;
import com.example.Blogs.Models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userDAO.getUserCredential(email);
            log.info("User found: {} , GREAT SUCCESS", user.getId());
        } catch (Exception e) {
            log.error("User not found: {} , TRY AGAIN , {}", email , e.getMessage());
            throw new UsernameNotFoundException("User not found");
        }
        return UserDetailsImpl.build(user);
    }

    public UserDetailsImpl loadUserById(Long id) throws UsernameNotFoundException {

        User userOptional = userDAO.findByIdUserDetails(id);
        if(userOptional == null){
            throw new UsernameNotFoundException("User not found");
        }
        return UserDetailsImpl.build(userOptional);
    }
}
