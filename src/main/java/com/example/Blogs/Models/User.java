package com.example.Blogs.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Table("users")
@AllArgsConstructor
public class User {
    @Id
    private Long id;
    private String username;

    @Column("display_name")
    private String displayName;

    private String email;
    private String password;

    @Column("signed_up_at")
    private LocalDateTime signedUpAt;

    private List<Post> posts;



    public User(long id, String username, String displayName, LocalDateTime signedUpAt) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.signedUpAt = signedUpAt;
        this.posts= new ArrayList<Post>();
    }

    public User(Long id , String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
