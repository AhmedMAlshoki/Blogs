package com.example.Blogs.Services;

import com.example.Blogs.DAOs.UserDAO;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Mappers.MapStructMappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserMapper userMapper, UserDAO userDAO) {
        this.userMapper = userMapper;
        this.userDAO = userDAO;
    }

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
