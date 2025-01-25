package com.arseniy.socialmediaapi.followers.exceptions;

public class FollowDoesNotExist extends RuntimeException {
    public FollowDoesNotExist(String message) {
        super(message);
    }
}
