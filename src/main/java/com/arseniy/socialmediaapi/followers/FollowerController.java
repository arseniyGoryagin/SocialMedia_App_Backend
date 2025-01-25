package com.arseniy.socialmediaapi.followers;


import com.arseniy.socialmediaapi.auth.domain.TokenResponse;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.followers.domain.FollowRequest;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
@Tag(name= "Followers")
public class FollowerController {

    private final FollowerService followerService;
  
    @Operation(summary = "Follow user")
    @ApiResponse(responseCode = "200", description = "User successfully followed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    @PostMapping()
    public ResponseEntity<MessageResponse> followUser(@AuthenticationPrincipal UserDetails userDetails,@RequestBody FollowRequest request) throws NoSuchException {
        followerService.followUser(request.getUsername(), userDetails.getUsername());
        return new ResponseEntity<>(new MessageResponse("User followed"), HttpStatus.OK);
    }


    @Operation(summary = "Unfollow user")
    @ApiResponse(responseCode = "200", description = "User successfully unfollowed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> unFollowUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("username") String username) throws NoSuchException {
        followerService.unFollowUser(username, userDetails.getUsername());
        return new ResponseEntity<>(new MessageResponse("User unfollowed"), HttpStatus.OK);
    }

}
