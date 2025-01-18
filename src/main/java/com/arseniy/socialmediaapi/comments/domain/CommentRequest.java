package com.arseniy.socialmediaapi.comments.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Comment request")
public class CommentRequest {

    @Schema(description = "Comment body", example = "Super!")
    @NotBlank
    private String body;


    @Schema(description = "Id of the post to comment on", example = "1")
    @NotBlank
    private Long postId;
}
