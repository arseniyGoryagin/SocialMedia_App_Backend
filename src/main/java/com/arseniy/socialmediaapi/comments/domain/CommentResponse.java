package com.arseniy.socialmediaapi.comments.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Comment response")
public class CommentResponse {

    @Schema(description = "Id of the comment", example = "1")
    private Long id;

    @Schema(description = "The body of teh comment", example = "Great!")
    private String body;

    @Schema(description = "Name of the user who made the comment", example = "")
    private String name;

    @Schema(description = "User username", example = "user12345")
    private String username;

    @Schema(description = "User profile picture", example = "http://picture")
    private String profilePicture;

    @Schema(description = "Time when the comment was posted", example = "10:09:2003")
    private LocalDateTime timePosted;

    @Schema(description = "Number of likes", example = "1000")
    private Long likes;

    @Schema(description = "Is the post of the current user", example = "true")
    private Boolean isOwn;
}
