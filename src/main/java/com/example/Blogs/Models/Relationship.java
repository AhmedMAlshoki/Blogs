package com.example.Blogs.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("relationships")
public class Relationship {
    @Id
    private Long id;
    private AggregateReference<User,Integer> follower;
    private AggregateReference<User,Integer> following;

    public Relationship(AggregateReference<User,Integer> follower,AggregateReference<User,Integer> following) {
        this.follower = follower;
        this.following = following;
    }
}
