package com.arseniy.socialmediaapi.posts.domain.dto;


import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class PostResponse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String body;

    private Long likes;

    private UserResponse user;

    private Boolean edited;


}
