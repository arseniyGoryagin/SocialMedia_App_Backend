package com.arseniy.socialmediaapi.posts.domain.dto;


import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
public class PostResponse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String body;

    private Long likes;
    private Boolean liked;

    private String username;
    private String name;
    private String profilePicture;

    private Boolean edited;

    private LocalDateTime timePosted;


}
