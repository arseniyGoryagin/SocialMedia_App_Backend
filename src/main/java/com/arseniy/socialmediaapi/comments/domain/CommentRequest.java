package com.arseniy.socialmediaapi.comments.domain;


import lombok.Data;

@Data
public class CommentRequest {
    private String body;
    private Long postId;

}
