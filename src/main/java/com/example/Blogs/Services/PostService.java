package com.example.Blogs.Services;

import com.example.Blogs.AuthenticationObject.AdvancedEmailPasswordToken;
import com.example.Blogs.DAOs.PostDAO;
import com.example.Blogs.DTOs.PostDTO;
import com.example.Blogs.Enums.Timezone;
import com.example.Blogs.Exceptions.PostNotFoundException;
import com.example.Blogs.Mappers.MapStructMappers.PostMapper;
import com.example.Blogs.Models.Post;
import com.example.Blogs.ScheduleJobs.TopPostsJob;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PostService {
    private final PostDAO postDAO;
    private final PostMapper postMapper;
    @Setter
    private  AdvancedEmailPasswordToken advancedEmailPasswordToken;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String TOP_POSTS_ORDER_KEY = "top_posts_order";
    private static final String POST_PREFIX = "post:";
    private final TopPostsJob topPostsJob;

    @Autowired
    public PostService(PostDAO postDAO, PostMapper postMapper, RedisTemplate<String, Object> redisTemplate,
                       TopPostsJob topPostsJob) {
        this.postDAO = postDAO;
        this.postMapper = postMapper;
        this.advancedEmailPasswordToken = (AdvancedEmailPasswordToken) SecurityContextHolder.getContext().getAuthentication();
        this.redisTemplate = redisTemplate;
        this.topPostsJob = topPostsJob;
    }

    public PostDTO getPost(Long id) {
        return postMapper.postToPostDTO(postDAO.findById(id));
    }


    public List<PostDTO> getUserPosts(Long id) {
       try{
           List<Post> posts = postDAO.findByUser(id);
           return posts.stream().map(postMapper::postToPostDTO).toList();
       }
       catch (PostNotFoundException e) {
           throw new PostNotFoundException("Post with ID " + id + " not found.");
       }
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
        int startIndex = (offset-1) * 20;
        int endIndex = startIndex + 19;

        List<Object> postIds = redisTemplate.opsForList().range(TOP_POSTS_ORDER_KEY, startIndex, endIndex);

        if (postIds == null || postIds.isEmpty()) {
            // Fallback to database if cache is empty
            log.info("Cache miss - falling back to database");
            return getTopPostsFromDB(offset);
        }

        List<PostDTO> posts = new ArrayList<>();

        for (Object postIdObj : postIds) {
            Long postId = (Long) postIdObj;
            String postKey = POST_PREFIX + postId;
            PostDTO postDTO = (PostDTO) redisTemplate.opsForValue().get(postKey);

            if (postDTO != null) {
                posts.add(postDTO);
            } else {
                // If individual post is missing, fetch from DB
                Post dbPost = postDAO.findById(postId);
                if (dbPost != null) {
                    PostDTO dto = postMapper.postToPostDTO(dbPost);
                    // Re-cache the missing post
                    redisTemplate.opsForValue().set(postKey, dto, Duration.ofHours(12));
                    posts.add(dto);
                }
            }
        }

        return posts;
    }

    private List<PostDTO> getTopPostsFromDB(Integer offset) {
        List<Post> posts = postDAO.findTopPostsOffset(offset);
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

    public PostDTO updatePost(Long id, String body, String title) throws PostNotFoundException {
        try{
            Post post = new Post(id, body, title);
            Timezone timezone = advancedEmailPasswordToken.getClientApiInfo().getTimezone();
            return postMapper.postToPostDTO(postDAO.updatePost(post,timezone));
        }catch (PostNotFoundException e) {
            throw new PostNotFoundException("Post with ID " + id + " not found.");
        }
    }

    @CacheEvict(cacheNames = "posts", key = "#id")
    public String deletePost(Long id) throws PostNotFoundException {
        try {
            return postDAO.deleteById(id);
        } catch (PostNotFoundException e) {
            throw new PostNotFoundException("Post with ID " + id + " not found.");
        }
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
