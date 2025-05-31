package com.example.Blogs.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
@Data
@Table("comments")
public class Comment {
    @Id
    private Long id; // Schema specifies INTEGER, but SERIAL is likely intended
    @Column("user_id")
    private Long userId;
    @Column("post_id")
    private Long postId;
    private String body;
    private LocalDateTime createdAt;

    public Comment(String body,Long user,Long post) {
        this.body = body;
        this.userId = user;
        this.postId = post;
        this.createdAt = LocalDateTime.now();

    }
    public Comment(String body,Long user,Long post,LocalDateTime createdAt) {
        this.body = body;
        this.userId = user;
        this.postId = post;
        this.createdAt = createdAt;
        this.createdAt = createdAt;

    }
}
