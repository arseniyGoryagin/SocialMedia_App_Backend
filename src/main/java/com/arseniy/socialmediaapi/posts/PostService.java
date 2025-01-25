package com.arseniy.socialmediaapi.posts;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.comments.CommentRepository;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.exceptions.NotAllowedException;
import com.arseniy.socialmediaapi.followers.FollowerRepository;
import com.arseniy.socialmediaapi.like.LikeRepository;
import com.arseniy.socialmediaapi.posts.domain.PostResponse;
import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {



    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    private final FollowerRepository followerRepository;

    private PostResponse toPostResponseFromPost(Post post, String currentUserUsername ){

        var user = post.getUser();

        return PostResponse.builder()
                .id(post.getId())
                .likes(likeRepository.countByPost_Id(post.getId()))
                .comments(commentRepository.countByPost_Id(post.getId()))
                .liked(likeRepository.existsByUser_UsernameAndPost_Id(currentUserUsername, post.getId()))
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


    public PostResponse addPost(String body, User user) throws EmailAlreadyInUseException {

        Post post = Post
                .builder()
                .edited(false)
                .body(body)
                .user(user)
                .timePosted(LocalDateTime.now())
                .build();


        return toPostResponseFromPost(postRepository.save(post), user.getUsername());
    }


    public void deletePost(String currentUserUsername, Long postId) throws NotAllowedException, NoSuchException {

        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty()){
            throw new NoSuchException("No such post");
        }

        if (!Objects.equals(post.get().getUser().getUsername(), currentUserUsername)){
            throw new NotAllowedException("This is not the users post");
        }

        postRepository.deleteAllById(Collections.singleton(postId));
    }

    public void editPost(String currentUserUsername, Long postId, String newBody) throws  NotAllowedException, NoSuchException {

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException());
        //TODO add proper errors

        if (!Objects.equals(post.getUser().getUsername(), currentUserUsername)){
            throw new NotAllowedException("This is not the users post");
        }

        post.setBody(newBody);
        post.setEdited(true);

        postRepository.save(post);
    }

    public Page<PostResponse> getAllUserPosts(String username, String currentUserUsername, Pageable pageable){
        return postRepository.findByUser_UsernameOrderByTimePostedDesc(username, pageable).map(post -> toPostResponseFromPost(post, currentUserUsername));
    }

    public Page<PostResponse> getUserLikes(String currentUserUsername, Pageable pageable) {
        return postRepository.findByUser_UsernameOrderByTimePostedDesc(currentUserUsername, pageable).map(post -> toPostResponseFromPost(post, currentUserUsername));
    }
}
