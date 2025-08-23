package com.example.Blogs.DAOs;

import com.example.Blogs.Models.Post;

import java.util.List;

public interface PostDAO {

    Post findById(Long id);
    boolean existsById(Long id);
    Post saveNewPost(Post post);
    Post updatePost(Post post);
    String deleteById(Long id);
    List<Post> findByUser(Long userId);
    List<Post> findFollowingUsersPosts(Long userId);
    List<Post> findPostsBySearchQuery(String searchQuery, List<Long> authorFilter, String minDate, String maxDate);
    List<Post> findTopPosts(Integer offset);
    String likePost(Long postId, Long userId);
    String dislikePost(Long postId, Long userId);
    
}
