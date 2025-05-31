package com.example.Blogs.DAOs;

import com.example.Blogs.Models.User;

import java.util.Map;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    Map<Long, User> findFollowers();
    Map<Long, User> findFollowing();
    Long[] findFollowingIds(Long userId);
    User saveNewUser(User user);
    void deleteById(Long id);
    boolean existsById(Long id);
    User update(User user);
}
