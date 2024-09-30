package com.arseniy.socialmediaapi.posts.domain.dto;

import com.arseniy.socialmediaapi.posts.domain.model.Post;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PostResponse {

    private final Post post;


}
