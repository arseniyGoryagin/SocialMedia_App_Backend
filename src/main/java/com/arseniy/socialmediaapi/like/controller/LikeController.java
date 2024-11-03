package com.arseniy.socialmediaapi.like.controller;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.like.domain.dto.LikeRequest;
import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.like.services.LikeService;
import com.arseniy.socialmediaapi.posts.domain.dto.PostResponse;
import com.arseniy.socialmediaapi.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;


    @PostMapping()
    public ResponseEntity<MessageResponse>  likePost(@RequestBody LikeRequest request) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        likeService.likePost(user.getUsername(), request.getPostId());

        return new ResponseEntity<>(new MessageResponse("Post liked"), HttpStatus.OK);
    }


    @DeleteMapping()
    public ResponseEntity<MessageResponse>  unlikePost(@RequestBody LikeRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        likeService.unlikePost(user.getUsername(), request.getPostId());

        return new ResponseEntity<>(new MessageResponse("Post unliked"), HttpStatus.OK);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception e){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());

        log.error(e.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
