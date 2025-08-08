package com.example.Blogs.Mappers;


import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Models.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    CommentDTO commentToCommentDTO(Comment comment);

    Comment commentDTOToComment(CommentDTO commentDTO);
}
