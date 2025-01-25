package com.arseniy.socialmediaapi.followers.exceptions;

public class FollowAlreadyExistsException extends RuntimeException {
    public FollowAlreadyExistsException(String message) {
        super(message);
    }
}
