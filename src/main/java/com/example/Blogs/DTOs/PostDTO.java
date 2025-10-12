package com.example.Blogs.DTOs;

import com.example.Blogs.Models.Like;
import com.example.Blogs.Serializers.OffsetDateTimeDeserializer;
import com.example.Blogs.Serializers.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private Long userId;
    private String body;
    private String title;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime createdAt;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime updated_at;
    private List<Like> likes;
    private UserDTO userDTO;
    public PostDTO(String body,Long user) {
        this.body = body;
        this.userId = user;
    }
    public PostDTO(Long id, Long userId, String body,String title, OffsetDateTime createdAt, List<Like> likes) {
        this.id = id;
        this.userId = userId;
        this.body = body;
        this.createdAt = createdAt;
        this.title = title;
        this.likes = likes;
    }

    public void applyTimeOffset(Long offset) {
        this.createdAt = this.createdAt.plusMinutes(offset);
        this.updated_at = this.updated_at.plusMinutes(offset);
    }
}
