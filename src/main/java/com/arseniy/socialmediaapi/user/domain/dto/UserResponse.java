package com.arseniy.socialmediaapi.user.domain.dto;


import com.arseniy.socialmediaapi.user.domain.model.User;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
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

    private Long followerCount;
    private Long followsCount;

    private Boolean isOwn;

    private Boolean isFollowing;

}
