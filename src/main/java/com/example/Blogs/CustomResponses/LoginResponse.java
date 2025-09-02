package com.example.Blogs.CustomResponses;

import com.example.Blogs.DTOs.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserDTO user;
}
