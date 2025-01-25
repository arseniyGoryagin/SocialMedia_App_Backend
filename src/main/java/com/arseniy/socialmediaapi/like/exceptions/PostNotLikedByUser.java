package com.arseniy.socialmediaapi.like.exceptions;

public class PostNotLikedByUser extends RuntimeException {
    public PostNotLikedByUser(String message) {
        super(message);
    }
}
