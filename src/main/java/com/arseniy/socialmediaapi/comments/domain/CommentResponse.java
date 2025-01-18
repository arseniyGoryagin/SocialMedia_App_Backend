package com.arseniy.socialmediaapi.comments.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long id;
    private String body;

    private String name;
    private String username;
    private String profilePicture;

    private LocalDateTime timePosted;

    private Long likes;
    private Boolean isOwn;
}
