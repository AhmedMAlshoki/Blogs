package com.example.Blogs.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
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





    public User(long id, String username, String displayName, LocalDateTime signedUpAt) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.signedUpAt = signedUpAt;
    }
}
