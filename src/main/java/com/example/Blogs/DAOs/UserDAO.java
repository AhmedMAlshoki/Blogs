package com.example.Blogs.DAOs;

import com.example.Blogs.Models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findFollowers(Long userId);
    List<User> findFollowing(Long userId);
    User saveNewUser(User user);
    String deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User update(User user);
    User getUserWithPosts(Long userId);
    User getUserCredential(String email);
}
