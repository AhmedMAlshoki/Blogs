package com.example.Blogs.DAOs;

import com.example.Blogs.Models.Post;

import java.util.List;

public interface PostDAO {

    Post findById(Long id);
    boolean existsById(Long id);
    int saveNewPost(Post post);
    Post updatePost(Post post);
    void deleteById(Long id);
    List<Post> findByUser(Long userId);
    List<Post> findFollowingUsersPosts(Long userId);
    List<Post> findPostsBySearchQuery(String searchQuery, List<Long> authorFilter, String minDate, String maxDate);
    List<Post> findTopPostsDaily();
    
}
