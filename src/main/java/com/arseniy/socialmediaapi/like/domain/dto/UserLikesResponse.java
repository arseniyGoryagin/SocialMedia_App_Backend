package com.arseniy.socialmediaapi.like.domain.dto;


import com.arseniy.socialmediaapi.like.domain.model.Like;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserLikesResponse {

    private final List<Like> likes;


}
