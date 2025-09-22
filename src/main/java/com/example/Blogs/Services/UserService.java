package com.example.Blogs.Services;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.DAOs.UserDAO;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Mappers.MapStructMappers.UserMapper;
import com.example.Blogs.Models.User;
import com.example.Blogs.Services.Security.UserDetailsImpl;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {


    private final UserMapper userMapper;
    private final UserDAO userDAO;
    private AdvancedEmailPasswordToken authentication;

    @Autowired
    public UserService(UserMapper userMapper, UserDAO userDAO) {
        this.userMapper = userMapper;
        this.userDAO = userDAO;
        this.authentication = (AdvancedEmailPasswordToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public  UserDTO  findByUsername(String username){
        return userMapper.userToUserDTO(userDAO.findByUsername(username).get());
    }

    public UserDTO findByEmail(String email){
        return userMapper.userToUserDTO(userDAO.findByEmail(email).get());
    }

    public UserDTO findById(Long id){
        return userMapper.userToUserDTO(userDAO.findById(id).get());
    }

    public List<UserDTO> findFollowers(Long id){
        List<User> followers = userDAO.findFollowers(id);
        return followers.stream().map(userMapper::userToUserDTO).toList();
    }

    public List<UserDTO> findFollowings(Long id){
        List<User> followings = userDAO.findFollowing(id);
        return followings.stream().map(userMapper::userToUserDTO).toList();
    }

    private String saveUser(User user){
        Timezone timezone = authentication.getClientApiInfo().getTimezone();
        return userDAO.saveNewUser(user,timezone);
    }

    public String deleteUser(Long id){
        return userDAO.deleteById(id);
    }

    public List<UserDTO> findByIds(List<Long> userIds) {
        List<User> users = userDAO.getByUserIds(userIds);
        return users.stream().map(userMapper::userToUserDTO).toList();
    }

    public String registerUser(String username, String displayName, String email, String password) {
        User user = new User(username, displayName, email, password);
        return saveUser(user);
    }

    public UserDTO updateUser(Long id,String username, String password, String email, String displayName) {
        User user = new User(id, username, displayName, email, password);
        Timezone timezone = authentication.getClientApiInfo().getTimezone();
        return userMapper.userToUserDTO(userDAO.update(user,timezone));
    }

    public UserDTO getUserFullProfile(Long id){
        return userMapper.userToUserDTO(userDAO.getUserWithPosts(id));
    }

    public String followUser(Long userId) {
        Long currentUserId = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        return userDAO.follow(currentUserId, userId);
    }

    public String unfollowUser(Long userId) {
        Long currentUserId = ((UserDetailsImpl)authentication.getPrincipal()).getId();
        return userDAO.unfollow(currentUserId, userId);
    }
}
