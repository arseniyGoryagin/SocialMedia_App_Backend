package com.arseniy.socialmediaapi.user.domain.dto;


import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UserResponse {

    private final Long id;
    private final String username;
    private final String description;
    private final String profilePicture;

}
