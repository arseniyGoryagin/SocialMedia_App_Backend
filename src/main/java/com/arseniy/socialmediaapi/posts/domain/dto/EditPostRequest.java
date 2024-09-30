package com.arseniy.socialmediaapi.posts.domain.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

public class EditPostRequest {
    private String newBody;
}
