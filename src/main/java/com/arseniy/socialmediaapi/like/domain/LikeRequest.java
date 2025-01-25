package com.arseniy.socialmediaapi.like.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LikeRequest {

    @NotBlank
    @Schema( description = "Id of the post", example = "1")
    private Long postId;

}
