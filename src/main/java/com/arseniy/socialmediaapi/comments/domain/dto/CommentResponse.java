package com.arseniy.socialmediaapi.comments.domain.dto;


import com.arseniy.socialmediaapi.comments.domain.model.Comment;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CommentResponse {
    private final List<Comment> comment;

}
