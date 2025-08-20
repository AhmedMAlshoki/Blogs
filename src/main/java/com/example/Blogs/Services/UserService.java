package com.example.Blogs.Services;

import com.example.Blogs.DTOs.UserDTO;

import java.util.List;

public class UserService {
    public List<UserDTO> findByIds(List<Long> userIds) {
        return null;
    }

    public String registerUser(String username, String displayName, String email, String password) {
        return null;
    }

    public UserDTO updateUser(String username, String password, String email, String displayName) {
        return null;
    }

    public String deleteUser(Long id) {
        return null;
    }

    public String followUser(Long userId) {
        return null;
    }

    public String unfollowUser(Long userId) {
        return null;
    }
}
