package com.arseniy.socialmediaapi.user.domain.projection;


public interface UserResponseProjection{
    Long getId();
    String getUsername();
    String getName();
    String getDescription();
    String getProfilePicture();
    Long getFollowerCount();
    Long getFollowsCount();
}
