package com.arseniy.socialmediaapi.posts.controllers;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.posts.domain.dto.EditPostRequest;
import com.arseniy.socialmediaapi.posts.domain.dto.PostRequest;
import com.arseniy.socialmediaapi.posts.domain.dto.PostResponse;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.posts.services.PostService;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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



    private List<PostResponse> toPostResponseFromPost(List<Post> posts){

        return  posts.stream().map(this::toPostResponseFromPost).collect(Collectors.toList());
    }

    private PostResponse toPostResponseFromPost(Post post){

            var user = post.getUser();

            UserResponse userResponse = UserResponse.builder()
                    .description(user.getDescription())
                    .username(user.getUsername())
                    .id(user.getId())
                    .profilePicture(user.getProfilePicture())
                    .build();

            return PostResponse.builder()
                    .id(post.getId())
                    .likes(post.getLikes())
                    .body(post.getBody())
                    .edited(post.getEdited())
                    .user(userResponse)
                    .build();
    }



    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getUserPosts(@PathVariable("username") String username, @RequestParam("limit") Long limit, @RequestParam("offset") Long offset) {

        log.info("username in cont = " + username);

        List<Post> posts = postService.getAllUserPosts(username, limit, offset);

        List<PostResponse> postResponses = toPostResponseFromPost(posts);

        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<List<PostResponse>> getFeed(@RequestParam("limit") Long limit, @RequestParam("offset") Long offset){

        List<Post> posts = postService.getPosts(limit, offset);
        return new ResponseEntity<>(toPostResponseFromPost(posts), HttpStatus.OK);

    }



    @PostMapping()
    public ResponseEntity<PostResponse> addPost(@RequestBody PostRequest request) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user  = (User) authentication.getPrincipal();

        Post post = postService.addPost( request.getBody(), user);

        return new ResponseEntity<>(toPostResponseFromPost(post), HttpStatus.OK);
    }




    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long postId) throws UserException {

        Optional<Post> post = postService.getPost(postId);

        if(post.isEmpty()){
            throw new UserException("No such post");
        }

        return new ResponseEntity<>(toPostResponseFromPost(post.get()), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editPost(@PathVariable("id") Long postId,  @RequestBody EditPostRequest request) throws UserException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();

        Optional<Post> post = postService.getPost(postId );

        if(post.isEmpty()){
            throw new UserException("No such post");
        }

        if (!Objects.equals(post.get().getUser().getUsername(), user.getUsername())){
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

        if (!Objects.equals(post.get().getUser().getUsername(), user.getUsername())){
            throw new UserException("You cannot delete someone elses pos");
        }


        postService.removePost(postId);

        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
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
