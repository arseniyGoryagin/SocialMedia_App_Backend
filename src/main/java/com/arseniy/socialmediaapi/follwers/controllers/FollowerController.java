package com.arseniy.socialmediaapi.follwers.controllers;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.follwers.domain.dto.FollowRequest;
import com.arseniy.socialmediaapi.follwers.domain.dto.FollowersResponse;
import com.arseniy.socialmediaapi.follwers.domain.model.Follow;
import com.arseniy.socialmediaapi.follwers.services.FollowerService;
import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/followers")
public class FollowerController {


    private final FollowerService followerService;



    @GetMapping("/{username}")
    public ResponseEntity<FollowersResponse> getUserFollowers(@PathVariable("username") String username,
                                                              @PathVariable("limit") Long limit,
                                                              @PathVariable("offset") Long offset) {

        List<Follow> followers = followerService.getUserFollowers(username, limit, offset);

        return new ResponseEntity<>(new FollowersResponse(followers), HttpStatus.OK);
    }



    @PostMapping()
    public ResponseEntity<String> followUser(@RequestBody FollowRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        followerService.followUser(user.getUsername(), request.getUsername());

        return new ResponseEntity<>("User followed", HttpStatus.OK);
    }


    @DeleteMapping()
    public ResponseEntity<String> unFollowUser(@RequestBody FollowRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        followerService.unFollowUser(user.getUsername(), request.getUsername());

        return new ResponseEntity<>("User followed", HttpStatus.OK);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
