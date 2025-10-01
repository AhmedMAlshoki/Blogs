package com.example.Blogs.Mappers.MapStructMappers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.DTOs.UserDTO;
import com.example.Blogs.Models.Post;
import com.example.Blogs.Models.User;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-01T20:36:17+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PostMapper postMapper;

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setDisplayName( user.getDisplayName() );
        userDTO.setPosts( longPostMapToLongPostDTOMap( user.getPosts() ) );

        return userDTO;
    }

    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setUsername( userDTO.getUsername() );
        user.setDisplayName( userDTO.getDisplayName() );
        user.setEmail( userDTO.getEmail() );
        user.setPassword( userDTO.getPassword() );
        user.setSignedUpAt( userDTO.getSignedUpAt() );
        user.setPosts( longPostDTOMapToLongPostMap( userDTO.getPosts() ) );

        return user;
    }

    protected Map<Long, PostDTO> longPostMapToLongPostDTOMap(Map<Long, Post> map) {
        if ( map == null ) {
            return null;
        }

        Map<Long, PostDTO> map1 = LinkedHashMap.newLinkedHashMap( map.size() );

        for ( java.util.Map.Entry<Long, Post> entry : map.entrySet() ) {
            Long key = entry.getKey();
            PostDTO value = postMapper.postToPostDTO( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected Map<Long, Post> longPostDTOMapToLongPostMap(Map<Long, PostDTO> map) {
        if ( map == null ) {
            return null;
        }

        Map<Long, Post> map1 = LinkedHashMap.newLinkedHashMap( map.size() );

        for ( java.util.Map.Entry<Long, PostDTO> entry : map.entrySet() ) {
            Long key = entry.getKey();
            Post value = postMapper.postDTOToPost( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
