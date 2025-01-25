package com.arseniy.socialmediaapi.comments.exceptions;

public class NoSuchCommentException extends RuntimeException {
    public NoSuchCommentException(String message) {
        super(message);
    }
}
