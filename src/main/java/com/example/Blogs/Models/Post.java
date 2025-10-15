package com.example.Blogs.Models;// Post.java

import com.example.Blogs.Serializers.OffsetDateTimeDeserializer;
import com.example.Blogs.Serializers.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table("posts")
@RedisHash("posts")
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    private Long id;
    private Long userId;
    private String body;
    private String title;
    @Column("created_at")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime createdAt;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime updated_at;
    private List<Like> likes = new ArrayList<Like>();
    public Post(String body,Long user) {
        this.body = body;
        this.userId = user;
        likes = new ArrayList<Like>();
    }

    public Post(long id, long userId, String body,String title, OffsetDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.body = body;
        this.createdAt = createdAt;
        this.title = title;
        List<Like> likes = new ArrayList<Like>();
    }
     public Post(Long id, String body, String title, Long userId)
     {
         this.id = id;
         this.body = body;
         this.title = title;
         this.userId = userId;
         this.likes = new ArrayList<Like>();
     }

    public Post(Long id, String body, String title)
    {
        this.id = id;
        this.body = body;
        this.title = title;
        this.likes = new ArrayList<Like>();
    }


    public Post(String body, String title, Long userId)
    {
        this.body = body;
        this.title = title;
        this.userId = userId;
        this.likes = new ArrayList<Like>();
    }

}
