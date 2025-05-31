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
    @Column("number_of_likes")
    private Integer numberOfLikes;
    @Column("number_of_comments")
    private Integer numberOfComments;
    private LocalDateTime updated_at;
    private List<Like> likes;
    private List<Comment> comments;
    public Post(String body,Long user) {
        this.body = body;
        this.userId = user;
        this.createdAt = LocalDateTime.now();
        this.numberOfLikes = 0;
        this.numberOfComments = 0;
    }

    public Post(long id, long userId, String body,String title, LocalDateTime createdAt, int numberOfLikes, int numberOfComments) {
        this.id = id;
        this.userId = userId;
        this.body = body;
        this.createdAt = createdAt;
        this.numberOfLikes = numberOfLikes;
        this.numberOfComments = numberOfComments;
        this.title = title;
        List<Like> likes = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
    }
}
