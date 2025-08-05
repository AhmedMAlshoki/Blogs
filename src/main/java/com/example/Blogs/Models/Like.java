package com.example.Blogs.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("likes")
@Data
public class Like {
    @Id
    private Long id;
    @Column("user_id")
    private Long userId;
    @JsonIgnore
    private LocalDateTime createdAt;

    public Like(Long user) {
        this.userId = user;
        this.createdAt = LocalDateTime.now();
    }

    public Like(Long user,LocalDateTime createdAt) {
        this.userId = user;
        this.createdAt = createdAt;
    }


}
