package com.example.Blogs.Mappers.MapStructMappers;

import com.example.Blogs.DTOs.CommentDTO;
import com.example.Blogs.Models.Comment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-27T19:27:27+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDTO commentToCommentDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setBody( comment.getBody() );
        commentDTO.setCreatedAt( comment.getCreatedAt() );
        commentDTO.setId( comment.getId() );
        commentDTO.setPostId( comment.getPostId() );
        commentDTO.setUserId( comment.getUserId() );

        return commentDTO;
    }

    @Override
    public Comment commentDTOToComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setBody( commentDTO.getBody() );
        comment.setCreatedAt( commentDTO.getCreatedAt() );
        comment.setId( commentDTO.getId() );
        comment.setPostId( commentDTO.getPostId() );
        comment.setUserId( commentDTO.getUserId() );

        return comment;
    }
}
