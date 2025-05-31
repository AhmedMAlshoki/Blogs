package com.example.Blogs.Exceptions;

public class ExictingUserException extends RuntimeException {
    public ExictingUserException(String message) {
        super(message);
    }
}
