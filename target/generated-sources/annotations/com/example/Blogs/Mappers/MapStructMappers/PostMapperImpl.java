package com.example.Blogs.Mappers.MapStructMappers;

import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Models.Like;
import com.example.Blogs.Models.Post;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-01T20:36:17+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setId( post.getId() );
        postDTO.setUserId( post.getUserId() );
        postDTO.setBody( post.getBody() );
        postDTO.setTitle( post.getTitle() );
        postDTO.setCreatedAt( post.getCreatedAt() );
        postDTO.setUpdated_at( post.getUpdated_at() );
        List<Like> list = post.getLikes();
        if ( list != null ) {
            postDTO.setLikes( new ArrayList<Like>( list ) );
        }

        return postDTO;
    }

    @Override
    public Post postDTOToPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( postDTO.getId() );
        post.setUserId( postDTO.getUserId() );
        post.setBody( postDTO.getBody() );
        post.setTitle( postDTO.getTitle() );
        post.setCreatedAt( postDTO.getCreatedAt() );
        post.setUpdated_at( postDTO.getUpdated_at() );
        List<Like> list = postDTO.getLikes();
        if ( list != null ) {
            post.setLikes( new ArrayList<Like>( list ) );
        }

        return post;
    }
}
