package com.example.Blogs.DTOs;

import com.example.Blogs.Models.Post;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@JsonRootName(value = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String displayName;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    @JsonRawValue
    private LocalDateTime signedUpAt;
    @Setter
    @JsonAnyGetter
    private Map<Long, PostDTO> posts;

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
