package com.arseniy.socialmediaapi.followers.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FollowersResponse {

    private final List<Follow> followers;


}
