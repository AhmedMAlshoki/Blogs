package com.example.Blogs.Config;

import com.example.Blogs.DAOs.*;
import com.example.Blogs.DAOs.SqlQueries.*;
import com.example.Blogs.Utils.DAOUtilities.DAOUtilities;
import com.example.Blogs.Utils.DAOUtilities.DAOUtilitiesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class QueryConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate();
    }
    @Bean
    public PostQueries postQueries() {
        return new PostPostgresQueries();
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

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImplement(jdbcTemplate(),userQueries(), daoUtilities());
    }
    @Bean
    public PostDAO postDAO() {
        return new PostDAOImplement(jdbcTemplate(), userDAO(), postQueries(), daoUtilities());
    }

    @Bean
    public CommentDAO commentDAO() {
        return new CommentDAOImplement(jdbcTemplate(), daoUtilities(), commentQueries(), userDAO(), postDAO());
    }
}
