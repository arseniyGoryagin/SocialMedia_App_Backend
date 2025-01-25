package com.arseniy.socialmediaapi.exceptions;

public class UserNotAllowedOperationException extends RuntimeException {
    public UserNotAllowedOperationException(String message) {
        super(message);
    }
}
