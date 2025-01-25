package com.arseniy.socialmediaapi.posts.exceptions;

public class NoSuchPostException extends RuntimeException {
    public NoSuchPostException(String message) {
        super(message);
    }
}
