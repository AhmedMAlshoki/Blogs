package com.example.Blogs.DTOs;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value="comment")
public class CommentDTO {
    private Long id;
    private Long postId;
    private String body;
    private Long userId;
    private LocalDateTime createdAt;
    public CommentDTO(String body,Long user,Long post) {
        this.body = body;
        this.userId = user;
        this.postId = post;
    }
    public CommentDTO(Long id,String body,Long post,Long user, LocalDateTime createdAt) {
        this.body = body;
        this.userId = user;
        this.postId = post;
        this.id = id;
        this.createdAt = createdAt;
    }
}
