package com.arseniy.socialmediaapi.comments;


import com.arseniy.socialmediaapi.comments.domain.CommentResponse;
import com.arseniy.socialmediaapi.comments.exceptions.NoSuchCommentException;
import com.arseniy.socialmediaapi.exceptions.UserNotAllowedOperationException;
import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.posts.PostRepository;
import com.arseniy.socialmediaapi.posts.exceptions.NoSuchPostException;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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
       return commentRepository.findByPostId(postId, pageable).map(comment -> toCommentResponse(comment, currentUser));
    }


    @Transactional
    public CommentResponse addComment(String currentUserUsername, Long postId, String body) throws NoSuchUserException, NoSuchPostException {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchPostException("Post with this post id does not exists"));
        User user = userRepository.findByUsername(currentUserUsername).orElseThrow(() -> new NoSuchUserException("No such user"));

        Comment comment = Comment.builder()
                .body(body)
                .post(post)
                .user(user)
                .timePosted(LocalDateTime.now())
                .build();

        return toCommentResponse(commentRepository.save(comment), currentUserUsername);
    }


    public void deleteComment(Long comId, String currentUser) throws NoSuchCommentException, UserNotAllowedOperationException {

        Comment comment = commentRepository.findById(comId).orElseThrow(() -> new NoSuchCommentException("No such comment"));

        if(comment.getUser().getUsername().equals(currentUser)){
            throw new UserNotAllowedOperationException("This comment does not belong to the user therefore cannot be deleted");
        }

        commentRepository.deleteById(comId);
    }


    public void editComment(Long comId, String newBody){
        Comment comment = commentRepository.findById(comId).orElseThrow(() -> new NoSuchCommentException("No such comment exists"));
        comment.setBody(newBody);
        commentRepository.save(comment);
    }



    // TODO liking comments


}
