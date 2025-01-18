package com.arseniy.socialmediaapi.like;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.like.domain.LikeRequest;
import com.arseniy.socialmediaapi.util.responses.MessageResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;


    @PostMapping()
    public ResponseEntity<MessageResponse>  likePost(@AuthenticationPrincipal UserDetails userDetails, @RequestBody LikeRequest request) throws EmailAlreadyInUseException, NoSuchException {
        likeService.likePost(userDetails.getUsername(), request.getPostId());
        return new ResponseEntity<>(new MessageResponse("Post liked"), HttpStatus.OK);
        
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<MessageResponse>  unlikePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("postId") Long postId){
        likeService.unlikePost(userDetails.getUsername(), postId);
        return new ResponseEntity<>(new MessageResponse("Post unliked"), HttpStatus.OK);
    }

}
