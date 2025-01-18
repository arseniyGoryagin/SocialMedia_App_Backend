package com.arseniy.socialmediaapi.posts.domain;

import com.arseniy.socialmediaapi.user.domain.UserResponseProjection;

import java.time.LocalDateTime;

public interface PostResponseProjection {

    Long getId();
    String getBody();
    Long getLikes();
    UserResponseProjection getUser();
    Boolean getEdited();
    LocalDateTime getTimePosted();
}