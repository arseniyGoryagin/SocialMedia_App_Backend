package com.arseniy.socialmediaapi.follwers.controllers;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.follwers.domain.dto.FollowRequest;
import com.arseniy.socialmediaapi.follwers.domain.dto.FollowersResponse;
import com.arseniy.socialmediaapi.follwers.domain.model.Follow;
import com.arseniy.socialmediaapi.follwers.services.FollowerService;
import com.arseniy.socialmediaapi.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowerController {


    private final FollowerService followerService;



    @PostMapping()
    public ResponseEntity<MessageResponse> followUser(@RequestBody FollowRequest request) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        followerService.followUser(request.getUsername(), user.getUsername());

        return new ResponseEntity<>(new MessageResponse("User followed"), HttpStatus.OK);
    }


    @DeleteMapping()
    public ResponseEntity<MessageResponse> unFollowUser(@RequestBody FollowRequest request) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        followerService.unFollowUser(request.getUsername(), user.getUsername());

        return new ResponseEntity<>(new MessageResponse("User unfollowed"), HttpStatus.OK);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
