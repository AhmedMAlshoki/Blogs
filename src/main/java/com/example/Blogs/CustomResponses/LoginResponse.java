package com.example.Blogs.CustomResponses;

import com.example.Blogs.DTOs.UserDTO;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GraphQLType(name = "LoginResponse")
public class LoginResponse {
    private String token;
    private UserDTO user;
}
