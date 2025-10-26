package com.example.Blogs.CustomResponses;


import com.example.Blogs.Models.SearchQueryPost;
import io.leangen.graphql.annotations.types.GraphQLType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@GraphQLType(name = "SearchQueryResult")
public class SearchQueryResult {
    List<SearchQueryPost> searchQueryPosts;
    Integer total;
}
