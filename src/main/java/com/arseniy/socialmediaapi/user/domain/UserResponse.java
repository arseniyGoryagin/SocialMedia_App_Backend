package com.arseniy.socialmediaapi.user.domain;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private  String username;
    private  String name;
    private  String description;
    private  String profilePicture;

    private int followerCount;
    private int followsCount;

    private Boolean isOwn;

    private Boolean isFollowing;

}
