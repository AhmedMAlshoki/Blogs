package com.example.Blogs.DTOs;

import com.example.Blogs.Serializers.OffsetDateTimeDeserializer;
import com.example.Blogs.Serializers.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private Long postId;
    private String body;
    private Long userId;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime createdAt;
    public CommentDTO(String body,Long user,Long post) {
        this.body = body;
        this.userId = user;
        this.postId = post;
    }
    public CommentDTO(Long id,String body,Long post,Long user, OffsetDateTime createdAt) {
        this.body = body;
        this.userId = user;
        this.postId = post;
        this.id = id;
        this.createdAt = createdAt;
    }

    public void applyTimeOffset(Long offset) {
        this.createdAt = this.createdAt.plusMinutes(offset);
    }
}
