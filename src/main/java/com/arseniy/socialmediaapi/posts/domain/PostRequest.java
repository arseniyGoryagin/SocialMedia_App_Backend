package com.arseniy.socialmediaapi.posts.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank
    @Schema( description = "body of the post", example = "Hello very one")
    private String body;

}
