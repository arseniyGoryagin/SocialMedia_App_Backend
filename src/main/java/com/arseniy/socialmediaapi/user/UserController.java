package com.arseniy.socialmediaapi.user;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.user.domain.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}/followers")
    public ResponseEntity<Page<UserResponse>> getUserFollowers(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) throws NoSuchException {
        Pageable pageable = PageRequest.of(page, size);
        var userResponse = userService.getUserFollowers(username,userDetails.getUsername(), pageable );
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<Page<UserResponse>> getUserFollows(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) throws  NoSuchException {
        Pageable pageable = PageRequest.of(page, size);
        var userResponse = userService.getUserFollows(username,userDetails.getUsername() , pageable );
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("username") String username) throws EmailAlreadyInUseException,  NoSuchException {;
        var userResponse = userService.getUser(username,  userDetails.getUsername());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUser(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("username") String searchUsername, @RequestParam("page") int page, @RequestParam("size") int size) throws NoSuchException {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> users  = userService.searchUsers(searchUsername, userDetails.getUsername(), pageable );
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) throws NoSuchException {
        var userResponse = userService.getUser(userDetails.getUsername(), userDetails.getUsername());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



}
