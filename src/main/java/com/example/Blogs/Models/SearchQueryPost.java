package com.example.Blogs.Models;

import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@AllArgsConstructor
@Data
@NoArgsConstructor
@GraphQLType(name = "SearchQueryPost")
public class SearchQueryPost {
    private Long id;
    private String title;
    private String body;
    private Long user;
    private String displayName;
    private OffsetDateTime publishedAt;
    private Float rank;
    private String highlight;
}
