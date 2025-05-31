package com.example.Blogs.DAOs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DAO_Implementaion {
    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    public DAO_Implementaion(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

