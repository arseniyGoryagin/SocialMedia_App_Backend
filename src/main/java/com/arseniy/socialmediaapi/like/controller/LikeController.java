package com.arseniy.socialmediaapi.like.controller;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.like.domain.dto.LikeRequest;
import com.arseniy.socialmediaapi.like.domain.dto.UserLikesResponse;
import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.like.services.LikeService;
import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping()
    public ResponseEntity<UserLikesResponse> getUserLikes(@RequestParam("limit") Long limit, @RequestParam("offset") Long offset){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        List<Like> likes = likeService.getUserLikes(user.getUsername(), limit, offset);

        return new ResponseEntity<>(new UserLikesResponse(likes), HttpStatus.OK);
    }


    @PostMapping("/like")
    public ResponseEntity<String>  likePost(@RequestBody LikeRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        likeService.likePost(user.getUsername(), request.getPostId());

        return new ResponseEntity<>("Post liked", HttpStatus.OK);
    }


    @PostMapping("/unlike")
    public ResponseEntity<String>  unlikePost(@RequestBody LikeRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();


        likeService.unlikePost(user.getUsername(), request.getPostId());

        return new ResponseEntity<>("Post unliked", HttpStatus.OK);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
