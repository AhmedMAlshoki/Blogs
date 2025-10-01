package com.example.Blogs.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Table("users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Long id;
    private String username;

    @Column("display_name")
    private String displayName;

    private String email;
    private String password;

    @Column("created_at")
    private OffsetDateTime signedUpAt;

    private Map<Long, Post> posts;


    public User(String username, String displayName, String email, String password) { //Constructor to create a new user
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.posts= new HashMap<Long, Post>();
    }

    public User(Long id, String username, String displayName, String email, String password) { //Constructor to update a  user
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.posts= new HashMap<Long, Post>();
    }

    public User(long id, String username, String displayName, OffsetDateTime signedUpAt) { //Constructor to Normal retrieve
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.signedUpAt = signedUpAt;
        this.posts= new HashMap<Long, Post>();
    }

    public User(Long id, String email, String password) { //Constructor to Login retrieve
        this.id = id;
        this.email = email;
        this.password = password;
    }

}
