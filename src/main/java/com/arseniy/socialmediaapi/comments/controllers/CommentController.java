package com.arseniy.socialmediaapi.comments.controllers;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.comments.domain.dto.CommentRequest;
import com.arseniy.socialmediaapi.comments.domain.dto.CommentsResponse;
import com.arseniy.socialmediaapi.comments.domain.dto.EditCommentRequest;
import com.arseniy.socialmediaapi.comments.domain.model.Comment;
import com.arseniy.socialmediaapi.comments.services.CommentService;
import com.arseniy.socialmediaapi.user.domain.model.User;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {


    private final CommentService commentService;


    @GetMapping("/{postId}")
    public ResponseEntity<CommentsResponse> getComments(@PathVariable("postId") Long id, @PathParam("limit") Long limit, @PathParam("offset") Long offset){
        List<Comment> commnets = commentService.getPostsComments(id, limit, offset);

        return new ResponseEntity<>(new CommentsResponse(commnets), HttpStatus.OK);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Comment> addComment( @PathParam("postId") Long postId, @RequestBody CommentRequest request){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Comment comment = commentService.addComment(user.getUsername(), postId, request.getBody());

        return new ResponseEntity<>(comment, HttpStatus.OK);

    }


    @DeleteMapping("/{comId}")
    public ResponseEntity<String> deleteComment(@PathVariable("comId") Long comId) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();


        Optional<Comment> comment = commentService.getComment(comId);

        if(comment.isEmpty()){
            throw new UserException("No such comment");
        }

        if(!comment.get().getUsername().equals(user.getUsername())){
            throw new UserException("Cannot delete someone else's comment");
        }

        commentService.deleteComment(comId);

        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }

    @PutMapping("/{comId}")
    public ResponseEntity<String> editComment(@PathVariable("comId") Long comId, @RequestBody EditCommentRequest request) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();


        Optional<Comment> comment = commentService.getComment(comId);

        if(comment.isEmpty()){
            throw new UserException("No such comment");
        }

        if(!comment.get().getUsername().equals(user.getUsername())){
            throw new UserException("Cannot delete someone else's comment");
        }

        commentService.editComment(comId, request.getBody());

        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }




    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleUserException(Exception e){

        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());

        return new ResponseEntity< ErrorResponse >(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleUserException(UserException e){

        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());

        return new ResponseEntity< ErrorResponse >(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
