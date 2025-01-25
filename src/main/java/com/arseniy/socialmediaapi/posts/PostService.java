package com.arseniy.socialmediaapi.posts;


import com.arseniy.socialmediaapi.comments.CommentRepository;
import com.arseniy.socialmediaapi.exceptions.UserNotAllowedOperationException;
import com.arseniy.socialmediaapi.posts.domain.PostResponse;
import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.posts.exceptions.NoSuchPostException;
import com.arseniy.socialmediaapi.user.UserService;
import com.arseniy.socialmediaapi.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    private PostResponse toPostResponseFromPost(Post post, String currentUserUsername ){

        var user = post.getUser();

        return PostResponse.builder()
                .id(post.getId())
                .likes(userService.getLikedPostsIds(currentUserUsername).size())
                .comments(commentRepository.countByPostId(post.getId()))
                .liked(userService.getLikedPostsIds(currentUserUsername).contains(post.getId()))
                .body(post.getBody())
                .edited(post.getEdited())
                .username(user.getUsername())
                .profilePicture(user.getProfilePicture())
                .name(user.getName())
                .timePosted(post.getTimePosted())
                .isOwn(Objects.equals(currentUserUsername, user.getUsername()))
                .build();
    }



    public Page<PostResponse> getPosts(String currentUserUsername, Pageable pageable){
       return postRepository.findAll(pageable).map(post -> toPostResponseFromPost(post, currentUserUsername));
    }

    public Page<PostResponse> getFeed(String currentUserUsername, Pageable pageable){
        return postRepository.findAll(pageable).map(post -> toPostResponseFromPost(post, currentUserUsername));
    }


    public PostResponse addPost(String body, User user){

        Post post = Post
                .builder()
                .edited(false)
                .body(body)
                .user(user)
                .timePosted(LocalDateTime.now())
                .build();


        return toPostResponseFromPost(postRepository.save(post), user.getUsername());
    }


    public void deletePost(String currentUserUsername, Long postId) throws NoSuchPostException, UserNotAllowedOperationException {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchPostException("No such post"));


        if (!post.getUser().getUsername().equals(currentUserUsername)){
            throw new UserNotAllowedOperationException("User cannot delete someone elses post");
        }

        postRepository.deleteAllById(Collections.singleton(postId));
    }

    public void editPost(String currentUserUsername, Long postId, String newBody) throws NoSuchPostException, UserNotAllowedOperationException {

        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchPostException("No such post"));

        if (!post.getUser().getUsername().equals(currentUserUsername)){
            throw new UserNotAllowedOperationException("User cannot edit someone elses post");
        }

        post.setBody(newBody);
        post.setEdited(true);

        postRepository.save(post);
    }

    public Page<PostResponse> getAllUserPosts(String username, String currentUserUsername, Pageable pageable){
        return postRepository.findByUserUsernameOrderByTimePostedDesc(username, pageable).map(post -> toPostResponseFromPost(post, currentUserUsername));
    }



    //
    public Page<PostResponse> getAllPostsByIds(List<Long> postId, String currentUserUsername, Pageable pageable) {
        return postRepository.findByIdIn(postId, pageable).map(post -> toPostResponseFromPost(post, currentUserUsername));
    }
    public Page<PostResponse> getUserLikes(String currentUserUsername, Pageable pageable) {
        List<Long> likedIds = userService.getLikedPostsIds(currentUserUsername);
        return getAllPostsByIds(likedIds, currentUserUsername, pageable);
    }
}
