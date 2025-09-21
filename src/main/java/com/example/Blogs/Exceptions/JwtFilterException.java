package com.example.Blogs.Exceptions;

public class JwtFilterException extends RuntimeException {
    public JwtFilterException(String message) {
        super(message);
    }
}
