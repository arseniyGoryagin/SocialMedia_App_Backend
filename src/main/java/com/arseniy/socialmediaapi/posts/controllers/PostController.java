package com.arseniy.socialmediaapi.posts.controllers;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.posts.domain.dto.EditPostRequest;
import com.arseniy.socialmediaapi.posts.domain.dto.PostRequest;
import com.arseniy.socialmediaapi.posts.domain.dto.PostResponse;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.posts.services.PostService;
import com.arseniy.socialmediaapi.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {


    private final PostService postService;


    private String getCurrentUserUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();
        return user.getUsername();
    }




    @GetMapping("/user/{username}")
    public ResponseEntity<Page<PostResponse>> getUserPosts(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(postService.getAllUserPosts(username, getCurrentUserUsername(), pageable), HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<Page<PostResponse>> getFeed(@RequestParam("page") int page, @RequestParam("size") int size){

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(postService.getPosts( getCurrentUserUsername(),pageable), HttpStatus.OK);

    }



    @PostMapping()
    public ResponseEntity<MessageResponse> addPost(@RequestBody PostRequest request) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();


        var response = postService.addPost( request.getBody(), user);
        log.info(response.toString());

        return new ResponseEntity<>(new MessageResponse("Post added"), HttpStatus.OK);
    }




    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long postId) throws UserException {

        Optional<PostResponse> post = postService.getPost(postId);

        if(post.isEmpty()){
            throw new UserException("No such post");
        }

        return new ResponseEntity<>(post.get(), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> editPost(@PathVariable("id") Long postId, @RequestBody EditPostRequest request) throws UserException {




        Optional<PostResponse> post = postService.getPost(postId );

        if(post.isEmpty()){
            throw new UserException("No such post");
        }

        if (!Objects.equals(post.get().getUsername(), getCurrentUserUsername())){
            throw new UserException("You cannot edit someone else post");
        }

        postService.editPost(postId, request.getNewBody());
        return new ResponseEntity<>(new MessageResponse("Post edited"), HttpStatus.OK);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("id") Long postId) throws UserException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();

        Optional<PostResponse> post = postService.getPost(postId);

        if(post.isEmpty()){
            throw new UserException("No such post");
        }

        if (!Objects.equals(post.get().getUsername(), user.getUsername())){
            throw new UserException("You cannot delete someone elses pos");
        }


        postService.removePost(postId);

        return new ResponseEntity<>(new MessageResponse("Post deleted"), HttpStatus.OK);
    }


    @GetMapping("/likes/{username}")
    public ResponseEntity<Page<PostResponse>> getUserLikes(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size);

        Page<PostResponse> likes = postService.getUserLikes(user.getUsername(),pageable);

        return new ResponseEntity<>(likes, HttpStatus.OK);
    }


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserResponse(UserException e){

        ErrorResponse error = new ErrorResponse();

        log.error(e.getMessage());

        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUserResponse(Exception e){

        ErrorResponse error = new ErrorResponse();

        log.error(e.getLocalizedMessage());

        error.setMessage(e.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }






}
