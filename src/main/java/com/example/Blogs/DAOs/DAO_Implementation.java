package com.example.Blogs.DAOs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class DAO_Implementation {
    protected final JdbcTemplate jdbcTemplate;

    @Autowired
    public DAO_Implementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

