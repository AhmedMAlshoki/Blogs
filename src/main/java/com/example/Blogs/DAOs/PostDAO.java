package com.example.Blogs.DAOs;

import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Models.Post;

import java.util.List;

public interface PostDAO {

    Post findById(Long id);
    boolean existsById(Long id);
    Post saveNewPost(Post post , Timezone timezone);
    Post updatePost(Post post , Timezone timezone);
    String deleteById(Long id);
    List<Post> findByUser(Long userId);
    List<Post> findFollowingUsersPosts(Long userId);
    List<Post> findPostsBySearchQuery(String searchQuery, List<Long> authorFilter, String minDate, String maxDate, Integer limit ,Integer offset);
    List<Post> findTopPosts(Integer offset);
    String likePost(Long postId, Long userId, Timezone timezone);
    String dislikePost(Long postId, Long userId);
    Long getPostOwner(Long postId);
}
