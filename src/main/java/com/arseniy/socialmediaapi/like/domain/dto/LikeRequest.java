package com.arseniy.socialmediaapi.like.domain.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LikeRequest {

    private final Long postId;

}
