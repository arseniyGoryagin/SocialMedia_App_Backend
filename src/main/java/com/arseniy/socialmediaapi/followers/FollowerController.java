package com.arseniy.socialmediaapi.followers;


import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.followers.domain.FollowRequest;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowerController {


    private final FollowerService followerService;



    @PostMapping()
    public ResponseEntity<MessageResponse> followUser(@RequestBody FollowRequest request) throws NoSuchException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        followerService.followUser(request.getUsername(), user.getUsername());

        return new ResponseEntity<>(new MessageResponse("User followed"), HttpStatus.OK);
    }


    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> unFollowUser(@PathVariable("username") String username) throws NoSuchException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        followerService.unFollowUser(username, user.getUsername());

        return new ResponseEntity<>(new MessageResponse("User unfollowed"), HttpStatus.OK);
    }



}
