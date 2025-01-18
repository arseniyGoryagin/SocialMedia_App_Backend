package com.arseniy.socialmediaapi.comments;


import com.arseniy.socialmediaapi.auth.domain.TokenResponse;
import com.arseniy.socialmediaapi.comments.domain.CommentRequest;
import com.arseniy.socialmediaapi.comments.domain.CommentResponse;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.exceptions.NotAllowedException;
import com.arseniy.socialmediaapi.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Tag(name = "Comments")
public class CommentController {


    private final CommentService commentService;

    @Operation(summary = "Get comments on post")
    @ApiResponse(responseCode = "200", description = "Comments retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)))
    @GetMapping("/{postId}")
    public ResponseEntity<Page<CommentResponse>> getComments(@PathVariable("postId") Long id, @PathParam("page") int page, @PathParam("size") int size){

        Pageable pageable = PageRequest.of(page, size);

        String currentUserUsername = Util.getCurrentUserUsername();

        Page<CommentResponse> comments = commentService.getPostsComments(id, currentUserUsername ,pageable);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    @Operation(summary = "Make a comment")
    @ApiResponse(responseCode = "200", description = "Comment made", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)))
    @PostMapping()
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request) throws NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        CommentResponse comment = commentService.addComment(currentUserUsername, request.getPostId(), request.getBody());

        return new ResponseEntity<>(comment, HttpStatus.OK);

    }


    @Operation(summary = "Delete comment")
    @ApiResponse(responseCode = "200", description = "Comment deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @DeleteMapping("/{comId}")
    public ResponseEntity<String> deleteComment(@PathVariable("comId") Long comId) throws NotAllowedException, NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        commentService.deleteComment(comId, currentUserUsername);

        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }


}
