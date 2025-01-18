package com.arseniy.socialmediaapi.comments;


import com.arseniy.socialmediaapi.comments.domain.CommentResponse;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.exceptions.NotAllowedException;
import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.posts.PostRepository;
import com.arseniy.socialmediaapi.updates.domain.Update;
import com.arseniy.socialmediaapi.updates.UpdateService;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    private final UpdateService updateService;


    private CommentResponse toCommentResponse(Comment comment, String currentUsername){
        return CommentResponse.builder()
                .name (comment.getUser().getName())
                .profilePicture(comment.getUser().getProfilePicture())
               // .likes(comment.getLikes())
                .likes(0L)
                .body(comment.getBody())
                .id(comment.getId())
                .isOwn(Objects.equals(currentUsername, comment.getUser().getUsername()))
                .timePosted(comment.getTimePosted())
                .username(comment.getUser().getUsername())
                .build();
    }


    public Page<CommentResponse> getPostsComments(Long postId, String currentUser, Pageable pageable){
       return commentRepository.findByPost_Id(postId, pageable).map(comment -> toCommentResponse(comment, currentUser));
    }


    @Transactional
    public CommentResponse addComment(String currentUserUsername, Long postId, String body) throws NoSuchException {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchException("No such user"));
        User user = userRepository.findByUsername(currentUserUsername).orElseThrow(() -> new NoSuchException("No such user"));

        Comment comment = Comment.builder()
                .body(body)
                .post(post)
                .user(user)
                .timePosted(LocalDateTime.now())
                .build();


        if(!Objects.equals(post.getUser().getUsername(), currentUserUsername)) {
            updateService.makeUpdate(post.getUser().getUsername(), currentUserUsername, currentUserUsername + " commented on your post", Update.Type.Comment);
        }

        return toCommentResponse(commentRepository.save(comment), currentUserUsername);
    }


    public void deleteComment(Long comId, String currentUser) throws NoSuchException, NotAllowedException {


        Optional<Comment> comment = commentRepository.findById(comId);

        if(comment.isEmpty()){
            throw new NoSuchException("No such comment");
        }

        if(comment.get().getUser().getUsername() != currentUser){
            throw new NotAllowedException("This comment does not belong to the user therfore cannot be deleted");
        }

        commentRepository.deleteById(comId);
    }


    public void editComment(Long comId, String newBody){
        Comment comment = commentRepository.findById(comId).orElseThrow(() -> new RuntimeException());
        // TODO add correct error
        comment.setBody(newBody);
        commentRepository.save(comment);
    }



    // TODO liking


}
