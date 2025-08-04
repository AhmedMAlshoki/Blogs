package com.example.Blogs.Models;// Post.java

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table("posts")
@AllArgsConstructor
public class Post {
    @Id
    private Long id;
    private Long userId;
    private String body;
    private String title;
    @Column("created_at")
    private LocalDateTime createdAt;
    private LocalDateTime updated_at;
    private List<Like> likes;
    public Post(String body,Long user) {
        this.body = body;
        this.userId = user;
    }

    public Post(long id, long userId, String body,String title, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.body = body;
        this.createdAt = createdAt;
        this.title = title;
        List<Like> likes = new ArrayList<Like>();
    }


}
