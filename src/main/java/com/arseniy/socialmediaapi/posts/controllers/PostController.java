package com.arseniy.socialmediaapi.posts.controllers;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.posts.domain.dto.EditPostRequest;
import com.arseniy.socialmediaapi.posts.domain.dto.PostRequest;
import com.arseniy.socialmediaapi.posts.domain.dto.PostResponse;
import com.arseniy.socialmediaapi.posts.domain.dto.PostsResponse;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.posts.services.PostService;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import jakarta.persistence.Id;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {


    private final PostService postService;


    @GetMapping()
    public ResponseEntity<PostsResponse> getPosts(@RequestParam("limit") Long limit, @RequestParam("offset") Long offset){

        List<Post> posts = postService.getPosts(limit, offset);
        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);

    }



    @PostMapping()
    public ResponseEntity<PostResponse> addPost(@RequestBody PostRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user  = (User) authentication.getPrincipal();

        Post posts = postService.addPost(request.getTitle(), request.getBody(), user.getUsername());

        return new ResponseEntity<>(new PostResponse(posts), HttpStatus.OK);
    }




    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long postId) throws UserException {

        Optional<Post> posts = postService.getPost(postId);

        if(posts.isEmpty()){
            throw new UserException("No such post");
        }

        return new ResponseEntity<>(new PostResponse(posts.get()), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editPost(@PathVariable("id") Long postId,  @RequestBody EditPostRequest request) throws UserException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();

        Optional<Post> post = postService.getPost(postId );

        if(post.isEmpty()){
            throw new UserException("No such post");
        }

        if (!Objects.equals(post.get().getUsername(), user.getUsername())){
            throw new UserException("You cannot edit someone else post");
        }

        postService.editPost(postId, request.getNewBody());
        return new ResponseEntity<>("Post edited", HttpStatus.OK);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId) throws UserException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();

        Optional<Post> post = postService.getPost(postId);

        if(post.isEmpty()){
            throw new UserException("No such post");
        }

        if (!Objects.equals(post.get().getUsername(), user.getUsername())){
            throw new UserException("You cannot delete someone elses pos");
        }


        postService.removePost(postId);

        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
    }


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserResponse(UserException e){

        ErrorResponse error = new ErrorResponse();

        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUserResponse(Exception e){

        ErrorResponse error = new ErrorResponse();

        error.setMessage(e.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @GetMapping("/user/{username}")
    public ResponseEntity<PostsResponse> getUserPosts(@PathVariable("username") String username, @RequestParam("limit") Long limit, @RequestParam("offset") Long offset){

       // log.info("Getting possts !!!!!!!!!");

        List<Post> posts  = postService.getAllUserPosts(username, limit,   offset);

        return new ResponseEntity<>(new PostsResponse(posts), HttpStatus.OK);
    }




}
