package com.arseniy.socialmediaapi.followers.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class FollowersResponse {

    @NotBlank
    @Schema( description = "Follow objects")
    private final List<Follow> followers;

}
