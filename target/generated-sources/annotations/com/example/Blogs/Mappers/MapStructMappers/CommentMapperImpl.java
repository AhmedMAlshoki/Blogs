package com.example.Blogs.Mappers.MapStructMappers;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Models.Comment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-06T21:39:24+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDTO commentToCommentDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setPostId( comment.getPostId() );
        commentDTO.setBody( comment.getBody() );
        commentDTO.setUserId( comment.getUserId() );
        commentDTO.setCreatedAt( comment.getCreatedAt() );

        return commentDTO;
    }

    @Override
    public Comment commentDTOToComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDTO.getId() );
        comment.setUserId( commentDTO.getUserId() );
        comment.setPostId( commentDTO.getPostId() );
        comment.setBody( commentDTO.getBody() );
        comment.setCreatedAt( commentDTO.getCreatedAt() );

        return comment;
    }
}
