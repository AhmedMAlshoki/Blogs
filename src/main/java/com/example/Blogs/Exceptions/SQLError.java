package com.example.Blogs.Exceptions;

public class SQLError extends RuntimeException {
    public SQLError(String message) {
        super(message);
    }
}
