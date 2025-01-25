package com.arseniy.socialmediaapi.posts;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.exceptions.NotAllowedException;
import com.arseniy.socialmediaapi.posts.domain.EditPostRequest;
import com.arseniy.socialmediaapi.posts.domain.PostRequest;
import com.arseniy.socialmediaapi.posts.domain.PostResponse;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Posts")
public class PostController {


    private final PostService postService;



    @Operation(summary = "Like post")
    @ApiResponse(responseCode = "200", description = "Post liked", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    @GetMapping("/user/{username}")
    public ResponseEntity<Page<PostResponse>> getUserPosts(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(postService.getAllUserPosts(username,userDetails.getUsername() , pageable), HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity<Page<PostResponse>> getFeed(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("page") int page, @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(postService.getPosts(userDetails.getUsername(),pageable), HttpStatus.OK);

    }

  
   @PostMapping()
    public ResponseEntity<MessageResponse> addPost(@AuthenticationPrincipal UserDetails userDetails,@RequestBody PostRequest request) throws EmailAlreadyInUseException {
        postService.addPost( request.getBody(), (User) userDetails);
        return new ResponseEntity<>(new MessageResponse("Post added"), HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> editPost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long postId, @RequestBody EditPostRequest request) throws  NotAllowedException, NoSuchException {
        postService.editPost( userDetails.getUsername(),postId, request.getNewBody());
        return new ResponseEntity<>(new MessageResponse("Post edited"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long postId) throws NotAllowedException, NoSuchException {
        postService.deletePost(userDetails.getUsername(),  postId);
        return new ResponseEntity<>(new MessageResponse("Post deleted"), HttpStatus.OK);
    }


    @GetMapping("/likes/{username}")
    public ResponseEntity<Page<PostResponse>> getUserLikes(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponse> likes = postService.getUserLikes(userDetails.getUsername(),pageable);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }





}
