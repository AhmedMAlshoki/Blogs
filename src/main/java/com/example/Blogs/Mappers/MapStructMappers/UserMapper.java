package com.example.Blogs.Mappers.MapStructMappers;


import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {PostMapper.class})
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
