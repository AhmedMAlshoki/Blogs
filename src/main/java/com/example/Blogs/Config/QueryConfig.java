package com.example.Blogs.Config;

import com.example.Blogs.DAOs.DAOUtilities.*;
import com.example.Blogs.DAOs.SqlQueries.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfig {
    @Bean
    public PostQueries postQueries() {
        return new PostPostgresQueries(daoUtilities());
    }

    @Bean
    public UserQueries userQueries() {
        return new UserPostgresQueries();
    }

    @Bean
    public CommentQueries commentQueries() { return new CommentPostgresQueries(); }


    @Bean
    public DAOUtilities daoUtilities() {
        return new DAOUtilitiesImp();
    }
}
