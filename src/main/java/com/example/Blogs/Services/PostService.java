package com.example.Blogs.Services;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.DAOs.PostDAO;
import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Mappers.MapStructMappers.PostMapper;
import com.example.Blogs.Models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private final PostDAO postDAO;
    private final PostMapper postMapper;
    private final AdvancedEmailPasswordToken advancedEmailPasswordToken;

    @Autowired
    public PostService(PostDAO postDAO, PostMapper postMapper) {
        this.postDAO = postDAO;
        this.postMapper = postMapper;
        this.advancedEmailPasswordToken = (AdvancedEmailPasswordToken) SecurityContextHolder.getContext().getAuthentication();
    }

    public PostDTO getPost(Long id) {
        return postMapper.postToPostDTO(postDAO.findById(id));
    }


    public List<PostDTO> getUserPosts(Long id) {
        List<Post> posts = postDAO.findByUser(id);
        return posts.stream().map(postMapper::postToPostDTO).toList();
    }

    public List<PostDTO> getFollowingPosts(Long id) {
        List<Post> posts = postDAO.findFollowingUsersPosts(id);
        return  posts.stream().map(postMapper::postToPostDTO).toList();
    }

    public List<PostDTO> getPostsBySearchQuery(String searchQuery,
                                            List<Long> authorFilter,
                                            String minDate,
                                            String maxDate,
                                            Integer limit,
                                            Integer offset) {
        List<Post> posts = postDAO.findPostsBySearchQuery(searchQuery, authorFilter, minDate, maxDate, limit, offset);
        return posts.stream().map(postMapper::postToPostDTO).toList();
    }




    public List<PostDTO> getTopPosts(Integer offset) {
        List<Post> posts = postDAO.findTopPosts(offset);
        return posts.stream().map(postMapper::postToPostDTO).toList();
    }

    public PostDTO savePost(String body, String title) {
        Long userId = advancedEmailPasswordToken.getCurrentUserId();
        Timezone timezone = advancedEmailPasswordToken.getClientApiInfo().getTimezone();
        Post post = new Post(body,title,userId);
        return postMapper.postToPostDTO(postDAO.saveNewPost(post,timezone));
    }

    public boolean isUserAuthorized(Long id) {
        return Objects.equals(advancedEmailPasswordToken.getCurrentUserId(), getPostOwner(id));
    }

    private Long getPostOwner(Long id) {
        return postDAO.getPostOwner(id);

    }

    public PostDTO updatePost(Long id, String body, String title) {
        Post post = new Post(id, body, title);
        Timezone timezone = advancedEmailPasswordToken.getClientApiInfo().getTimezone();
        return postMapper.postToPostDTO(postDAO.updatePost(post,timezone));
    }

    public String deletePost(Long id) {
        return postDAO.deleteById(id);
    }

    public String likePost(Long id) {
        Long userId = advancedEmailPasswordToken.getCurrentUserId();
        Timezone timezone = advancedEmailPasswordToken.getClientApiInfo().getTimezone();
        return postDAO.likePost(id,userId,timezone);
    }

    public String dislikePost(Long id) {
        Long userId = advancedEmailPasswordToken.getCurrentUserId();
        return postDAO.dislikePost(id,userId);
    }
}
