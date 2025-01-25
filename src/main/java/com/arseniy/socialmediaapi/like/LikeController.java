package com.arseniy.socialmediaapi.like;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.like.domain.LikeRequest;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
@Tag(name= "Likes")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "Like post")
    @ApiResponse(responseCode = "200", description = "Post liked", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    @PostMapping()
    public ResponseEntity<MessageResponse>  likePost(@RequestBody LikeRequest request) throws EmailAlreadyInUseException, NoSuchException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        likeService.likePost(user.getUsername(), request.getPostId());

        return new ResponseEntity<>(new MessageResponse("Post liked"), HttpStatus.OK);
        
    }


    @Operation(summary = "Unlike post")
    @ApiResponse(responseCode = "200", description = "Post unliked", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    @DeleteMapping("/{postId}")
    public ResponseEntity<MessageResponse>  unlikePost(@PathVariable("postId") Long postId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        likeService.unlikePost(user.getUsername(), postId);

        return new ResponseEntity<>(new MessageResponse("Post unliked"), HttpStatus.OK);

    }

}
