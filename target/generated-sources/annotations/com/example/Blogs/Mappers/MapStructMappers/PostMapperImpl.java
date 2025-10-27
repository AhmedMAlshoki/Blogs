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
    date = "2025-10-27T19:27:27+0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setBody( post.getBody() );
        postDTO.setCreatedAt( post.getCreatedAt() );
        postDTO.setId( post.getId() );
        List<Like> list = post.getLikes();
        if ( list != null ) {
            postDTO.setLikes( new ArrayList<Like>( list ) );
        }
        postDTO.setTitle( post.getTitle() );
        postDTO.setUpdated_at( post.getUpdated_at() );
        postDTO.setUserId( post.getUserId() );

        return postDTO;
    }

    @Override
    public Post postDTOToPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setBody( postDTO.getBody() );
        post.setCreatedAt( postDTO.getCreatedAt() );
        post.setId( postDTO.getId() );
        List<Like> list = postDTO.getLikes();
        if ( list != null ) {
            post.setLikes( new ArrayList<Like>( list ) );
        }
        post.setTitle( postDTO.getTitle() );
        post.setUpdated_at( postDTO.getUpdated_at() );
        post.setUserId( postDTO.getUserId() );

        return post;
    }
}
