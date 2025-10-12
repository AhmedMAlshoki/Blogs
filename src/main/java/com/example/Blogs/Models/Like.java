package com.example.Blogs.Models;

import com.example.Blogs.Serializers.OffsetDateTimeDeserializer;
import com.example.Blogs.Serializers.OffsetDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Table("likes")
@Data
@NoArgsConstructor
public class Like {
    @Id
    private Long id;
    @Column("user_id")
    private Long userId;
    @JsonIgnore
    private OffsetDateTime createdAt;

    public Like(Long user) {
        this.userId = user;
    }

    public Like(Long user, OffsetDateTime createdAt) {
        this.userId = user;
        this.createdAt = createdAt;
    }


}
