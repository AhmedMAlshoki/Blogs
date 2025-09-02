package com.example.Blogs.Exceptions;

public class HandlingRequestException extends RuntimeException {
    public HandlingRequestException(String message) {
        super(message);
    }
}
