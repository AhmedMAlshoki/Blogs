package com.example.Blogs.Config.RedisConfugurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
@Configuration
public class PostsRedisConfiguration  {

    @Autowired
    private RedisCacheConfiguration defaultCacheConfiguration;

    @Bean
    public RedisCacheConfiguration postsCacheConfiguration() {
        return defaultCacheConfiguration
                .prefixCacheNameWith("post:")
                .entryTtl(Duration.ofHours(2));
    }
}
