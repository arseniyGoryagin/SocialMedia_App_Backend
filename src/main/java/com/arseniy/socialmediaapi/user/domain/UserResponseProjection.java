package com.arseniy.socialmediaapi.user.domain;


public interface UserResponseProjection{
    Long getId();
    String getUsername();
    String getName();
    String getDescription();
    String getProfilePicture();
    Long getFollowerCount();
    Long getFollowsCount();
}