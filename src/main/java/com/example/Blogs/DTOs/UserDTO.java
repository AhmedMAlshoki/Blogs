package com.example.Blogs.DTOs;

import com.example.Blogs.Models.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String displayName;
    private String email;
    private String password;
    private LocalDateTime signedUpAt;
    @Setter
    private List<PostDTO> posts;

    public UserDTO(Long id, String username, String displayName, LocalDateTime signedUpAt) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.signedUpAt = signedUpAt;
    }

    public UserDTO(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserDTO(String username, String displayName, String email, String password) { //Constructor to create a new user
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }



}
