package com.arseniy.socialmediaapi.comments;


import com.arseniy.socialmediaapi.comments.domain.CommentRequest;
import com.arseniy.socialmediaapi.comments.domain.CommentResponse;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.exceptions.NotAllowedException;
import com.arseniy.socialmediaapi.util.Util;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {


    private final CommentService commentService;


    @GetMapping("/{postId}")
    public ResponseEntity<Page<CommentResponse>> getComments(@PathVariable("postId") Long id, @PathParam("page") int page, @PathParam("size") int size){

        Pageable pageable = PageRequest.of(page, size);

        String currentUserUsername = Util.getCurrentUserUsername();

        Page<CommentResponse> comments = commentService.getPostsComments(id, currentUserUsername ,pageable);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request) throws NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        CommentResponse comment = commentService.addComment(currentUserUsername, request.getPostId(), request.getBody());

        log.info(comment.toString());

        return new ResponseEntity<>(comment, HttpStatus.OK);

    }


    @DeleteMapping("/{comId}")
    public ResponseEntity<String> deleteComment(@PathVariable("comId") Long comId) throws NotAllowedException, NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        commentService.deleteComment(comId, currentUserUsername);

        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }



    /*@PutMapping("/{comId}")
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
    }*/


}
