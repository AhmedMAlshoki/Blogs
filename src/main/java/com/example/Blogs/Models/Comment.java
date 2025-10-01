package com.example.Blogs.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Table("comments")
@NoArgsConstructor
public class Comment {
    @Id
    private Long id; // Schema specifies INTEGER, but SERIAL is likely intended
    @Column("user_id")
    private Long userId;
    @Column("post_id")
    private Long postId;
    private String body;
    @Column("created_at")
    private OffsetDateTime createdAt;

    public Comment(String body,Long user,Long post) {
        this.body = body;
        this.userId = user;
        this.postId = post;
    }


    public Comment(long id, String body, long commentPostId, long commentUserId, OffsetDateTime createdAt) {
        this.id = id;
        this.body = body;
        this.postId = commentPostId;
        this.userId = commentUserId;
        this.createdAt = createdAt;
    }
}
