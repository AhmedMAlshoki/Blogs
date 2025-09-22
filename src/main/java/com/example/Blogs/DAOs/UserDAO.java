package com.example.Blogs.DAOs;

import com.example.Blogs.Enums.Timezone;
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
    String saveNewUser(User user, Timezone timezone);
    String deleteById(Long id);
    List<User> getByUserIds(List<Long> ids);
    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User update(User user, Timezone timezone);
    User getUserWithPosts(Long userId);
    User getUserCredential(String email);
    String follow(Long followerId, Long followingId);
    String unfollow(Long followerId, Long followingId);
    User findByIdUserDetails(Long id);
}
