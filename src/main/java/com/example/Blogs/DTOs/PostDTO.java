package com.example.Blogs.DTOs;

import com.example.Blogs.Models.Like;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private Long userId;
    private String body;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updated_at;
    private List<Like> likes;
    public PostDTO(String body,Long user) {
        this.body = body;
        this.userId = user;
    }
    public PostDTO(Long id, Long userId, String body,String title, LocalDateTime createdAt, List<Like> likes) {
        this.id = id;
        this.userId = userId;
        this.body = body;
        this.createdAt = createdAt;
        this.title = title;
        this.likes = likes;
    }
}
