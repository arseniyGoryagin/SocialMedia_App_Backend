package com.arseniy.socialmediaapi.followers;


import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.followers.domain.FollowRequest;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.User;
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
public class FollowerController {

    private final FollowerService followerService;

    @PostMapping()
    public ResponseEntity<MessageResponse> followUser(@AuthenticationPrincipal UserDetails userDetails,@RequestBody FollowRequest request) throws NoSuchException {
        followerService.followUser(request.getUsername(), userDetails.getUsername());
        return new ResponseEntity<>(new MessageResponse("User followed"), HttpStatus.OK);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> unFollowUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("username") String username) throws NoSuchException {
        followerService.unFollowUser(username, userDetails.getUsername());
        return new ResponseEntity<>(new MessageResponse("User unfollowed"), HttpStatus.OK);
    }

}
