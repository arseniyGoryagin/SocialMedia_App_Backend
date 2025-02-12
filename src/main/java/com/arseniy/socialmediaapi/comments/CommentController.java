package com.arseniy.socialmediaapi.comments;


import com.arseniy.socialmediaapi.comments.domain.CommentRequest;
import com.arseniy.socialmediaapi.comments.domain.CommentResponse;
import com.arseniy.socialmediaapi.comments.exceptions.NoSuchCommentException;
import com.arseniy.socialmediaapi.exceptions.ErrorResponse;
import com.arseniy.socialmediaapi.posts.exceptions.NoSuchPostException;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Tag(name = "Comments")
public class CommentController {


    private final CommentService commentService;

    @Operation(summary = "Get comments on post")
    @ApiResponse(responseCode = "200", description = "Comments retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)))
    @GetMapping("/{postId}")
    public ResponseEntity<Page<CommentResponse>> getComments(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("postId") Long id, @PathParam("page") int page, @PathParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> comments = commentService.getPostsComments(id, userDetails.getUsername(), pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


    @Operation(summary = "Make a comment")
    @ApiResponse(responseCode = "200", description = "Comment made", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class)))
    @PostMapping()
    public ResponseEntity<CommentResponse> addComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentRequest request)  {
        CommentResponse comment = commentService.addComment(userDetails.getUsername(), request.getPostId(), request.getBody());
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


    @Operation(summary = "Delete comment")
    @ApiResponse(responseCode = "200", description = "Comment deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @DeleteMapping("/{comId}")
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("comId") Long comId) {
        commentService.deleteComment(comId, userDetails.getUsername());
        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }


    @ExceptionHandler(NoSuchCommentException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchCommentException(NoSuchCommentException e){
        return new ResponseEntity<>(new ErrorResponse( e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchUserException(NoSuchUserException e){
        return new ResponseEntity<>(new ErrorResponse( e.getMessage()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NoSuchPostException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchPostException(NoSuchPostException e){
        return new ResponseEntity<>(new ErrorResponse( e.getMessage()), HttpStatus.NOT_FOUND);
    }



}
