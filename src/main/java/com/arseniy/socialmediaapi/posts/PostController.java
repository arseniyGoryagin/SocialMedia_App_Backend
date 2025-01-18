package com.arseniy.socialmediaapi.posts;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.exceptions.NotAllowedException;
import com.arseniy.socialmediaapi.posts.domain.EditPostRequest;
import com.arseniy.socialmediaapi.posts.domain.PostRequest;
import com.arseniy.socialmediaapi.posts.domain.PostResponse;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {


    private final PostService postService;




    @GetMapping("/user/{username}")
    public ResponseEntity<Page<PostResponse>> getUserPosts(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(postService.getAllUserPosts(username, Util.getCurrentUserUsername(), pageable), HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<Page<PostResponse>> getFeed(@RequestParam("page") int page, @RequestParam("size") int size){

        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(postService.getPosts( Util.getCurrentUserUsername(),pageable), HttpStatus.OK);

    }



    @PostMapping()
    public ResponseEntity<MessageResponse> addPost(@RequestBody PostRequest request) throws EmailAlreadyInUseException {

        User user  = Util.getCurrentUser();

        var response = postService.addPost( request.getBody(), user);
        log.info(response.toString());

        return new ResponseEntity<>(new MessageResponse("Post added"), HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> editPost(@PathVariable("id") Long postId, @RequestBody EditPostRequest request) throws  NotAllowedException, NoSuchException {

        postService.editPost( Util.getCurrentUserUsername() ,postId, request.getNewBody());

        return new ResponseEntity<>(new MessageResponse("Post edited"), HttpStatus.OK);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("id") Long postId) throws NotAllowedException, NoSuchException {

        User user  = Util.getCurrentUser();

        postService.deletePost(user.getUsername(),  postId);

        return new ResponseEntity<>(new MessageResponse("Post deleted"), HttpStatus.OK);
    }


    @GetMapping("/likes/{username}")
    public ResponseEntity<Page<PostResponse>> getUserLikes(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size){

        User user = Util.getCurrentUser();

        Pageable pageable = PageRequest.of(page, size);

        Page<PostResponse> likes = postService.getUserLikes(user.getUsername(),pageable);

        return new ResponseEntity<>(likes, HttpStatus.OK);
    }





}
