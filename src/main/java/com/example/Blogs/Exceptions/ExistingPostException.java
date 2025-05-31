package com.example.Blogs.Exceptions;

public class ExistingPostException extends RuntimeException {

    public ExistingPostException(String message) {
        super(message);
    }
}
