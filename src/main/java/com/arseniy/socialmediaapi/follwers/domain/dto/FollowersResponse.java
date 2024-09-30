package com.arseniy.socialmediaapi.follwers.domain.dto;

import com.arseniy.socialmediaapi.follwers.domain.model.Follow;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FollowersResponse {

    private final List<Follow> followers;


}
