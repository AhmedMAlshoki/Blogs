package com.example.Blogs.ScheduleJobs;


import com.example.Blogs.DAOs.PostDAO;
import com.example.Blogs.Models.Post;
import com.example.Blogs.Services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TopPostsJob {
     private final PostDAO postDAO;
     private final RedisTemplate<String, Object> redisTemplate;
     private static final String TOP_POSTS_ORDER_KEY = "top_posts_order";
     private static final String POST_PREFIX = "post:";
     private static final int TOTAL_TOP_POSTS = 2000;

    @Autowired
    public TopPostsJob(PostDAO postDAO, RedisTemplate<String, Object> redisTemplate) {
        this.postDAO = postDAO;
        this.redisTemplate = redisTemplate;
    }

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void cacheTopPosts() {
        log.info("Starting scheduled caching of top posts... ");

        clearTopPostsCache();

        List<Post> topPosts = postDAO.findTopPosts();

        // Limit to 2000 if more are returned
        if (topPosts.size() > TOTAL_TOP_POSTS) {
            topPosts = topPosts.subList(0, TOTAL_TOP_POSTS);
        }

        if (topPosts.isEmpty()) {
            log.info("No top posts found");
            return;
        }
        // 3. Store each post individually in cache
        List<Long> postIds = new ArrayList<>();

        // Use pipeline for better performance
        List<Post> finalTopPosts = topPosts;
        redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            for (Post post : finalTopPosts) {
                String postKey = POST_PREFIX + post.getId();
                redisTemplate.opsForValue().set(postKey, post, Duration.ofHours(2));
                postIds.add(post.getId());
            }
            return null;
        });

        // 4. Store the ordered list of post IDs (for pagination)
        redisTemplate.opsForList().rightPushAll(TOP_POSTS_ORDER_KEY, postIds.toArray());
        redisTemplate.expire(TOP_POSTS_ORDER_KEY, Duration.ofHours(2));

        log.info("Cached {} top posts successfully", topPosts.size());
    }

    public void clearTopPostsCache() {
        // Get all post IDs from the ordered list
        List<Object> postIds = redisTemplate.opsForList().range(TOP_POSTS_ORDER_KEY, 0, -1);

        if (postIds != null && !postIds.isEmpty()) {
            // Delete all individual post caches
            List<String> keysToDelete = postIds.stream()
                    .map(id -> POST_PREFIX + id)
                    .collect(Collectors.toList());

            redisTemplate.delete(keysToDelete);
        }

        // Delete the order list
        redisTemplate.delete(TOP_POSTS_ORDER_KEY);

        log.info("Cleared top posts cache");
    }

}
