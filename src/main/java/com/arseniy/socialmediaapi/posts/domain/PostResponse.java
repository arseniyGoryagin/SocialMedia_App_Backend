package com.arseniy.socialmediaapi.posts.domain;


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

    private int likes;
    private Boolean liked;

    private Boolean isOwn;

    private Long comments;

    private String username;
    private String name;
    private String profilePicture;

    private Boolean edited;

    private LocalDateTime timePosted;


}
