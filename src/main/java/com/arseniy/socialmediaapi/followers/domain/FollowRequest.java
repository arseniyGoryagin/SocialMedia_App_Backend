package com.arseniy.socialmediaapi.followers.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FollowRequest {

    @NotBlank
    @Schema( description = "users username", example = "12345Username")
    private String username;
}
