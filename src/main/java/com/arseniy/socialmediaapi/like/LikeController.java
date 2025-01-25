package com.arseniy.socialmediaapi.like;



import com.arseniy.socialmediaapi.exceptions.ErrorResponse;
import com.arseniy.socialmediaapi.like.domain.LikeRequest;
import com.arseniy.socialmediaapi.like.exceptions.PostNotLikedByUser;
import com.arseniy.socialmediaapi.posts.exceptions.NoSuchPostException;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
@Tag(name= "Likes")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "Like post")
    @ApiResponse(responseCode = "200", description = "Post liked", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    @PostMapping()
    public ResponseEntity<MessageResponse>  likePost(@AuthenticationPrincipal UserDetails userDetails, @RequestBody LikeRequest request) {
        likeService.likePost(userDetails.getUsername(), request.getPostId());
        return new ResponseEntity<>(new MessageResponse("Post liked"), HttpStatus.OK);
        
    }

    @Operation(summary = "Unlike post")
    @ApiResponse(responseCode = "200", description = "Post unliked", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    @DeleteMapping("/{postId}")
    public ResponseEntity<MessageResponse>  unlikePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("postId") Long postId){
        likeService.unlikePost(userDetails.getUsername(), postId);
        return new ResponseEntity<>(new MessageResponse("Post unliked"), HttpStatus.OK);
    }

    @ExceptionHandler(PostNotLikedByUser.class)
    public ResponseEntity<ErrorResponse> handlePostNotLikedByUserException(PostNotLikedByUser e){
        return new ResponseEntity<>(new ErrorResponse( e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchPostException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchPostException(NoSuchPostException e){
        return new ResponseEntity<>(new ErrorResponse( e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchUserException(NoSuchUserException e){
        return new ResponseEntity<>(new ErrorResponse( e.getMessage()), HttpStatus.NOT_FOUND);
    }


}
