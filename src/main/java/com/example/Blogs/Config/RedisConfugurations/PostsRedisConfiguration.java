package com.example.Blogs.Config.RedisConfugurations;

import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

public class PostsRedisConfiguration {

    public RedisCacheConfiguration postsCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith("post:")
                .entryTtl(Duration.ofHours(12));
    }
}
