package com.example.Blogs.Mappers.MapStructMappers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Models.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO postToPostDTO(Post post);
    Post postDTOToPost(PostDTO postDTO);
}
