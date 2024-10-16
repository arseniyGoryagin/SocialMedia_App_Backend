package com.arseniy.socialmediaapi.user.domain.dto;


import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private  String username;
    private  String description;
    private  String profilePicture;

}
