package com.arseniy.socialmediaapi.posts.services;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.follwers.repository.FollowerRepository;
import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.like.repository.LikeRepository;
import com.arseniy.socialmediaapi.posts.domain.dto.PostResponse;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.posts.repository.PostRepository;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {



    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    private final FollowerRepository followerRepository;

    private PostResponse toPostResponseFromPost(Post post){

        var user = post.getUser();

        return PostResponse.builder()
                .id(post.getId())
                .likes(likeRepository.countByPost_Id(post.getId()))
                //.liked(likeRepository.existsByUser_UsernameAndPost_Id(user.getUsername(), post.getId()))
                .body(post.getBody())
                .edited(post.getEdited())
                .username(user.getUsername())
                .profilePicture(user.getProfilePicture())
                .name(user.getName())
                .timePosted(post.getTimePosted())
                .build();
    }

    private PostResponse toLiked(String username, PostResponse post){
        post.setLiked(likeRepository.existsByUser_UsernameAndPost_Id(username, post.getId()));
        return post;
    }




    private Page<PostResponse> mapLiked(String username, Page<PostResponse> postResponses){
        return postResponses.map(post ->{return toLiked(username, post);});
    }


    public Page<PostResponse> getPosts(String currentUserUsername, Pageable pageable){
       var postResponses =  postRepository.getPosts(pageable).map(this::toPostResponseFromPost);
       return mapLiked(currentUserUsername, postResponses);
    }

    public Page<PostResponse> getFeed(String username, Pageable pageable){
        return postRepository.getPosts(pageable).map(this::toPostResponseFromPost);
    }


    public Optional<PostResponse> getPost(Long id){
        return postRepository.findById(id).map(this::toPostResponseFromPost);
    }


    public PostResponse addPost(String body, User user) throws UserException {

        Post post = Post
                .builder()
                .edited(false)
                .body(body)
                .user(user)
                .timePosted(LocalDateTime.now())
                .build();


        return toPostResponseFromPost(postRepository.save(post));
    }


    public void removePost(Long id){
        postRepository.deleteAllById(Collections.singleton(id));
    }

    public void editPost(Long postId, String newBody) {
        postRepository.editPost(postId, newBody);
    }

    public Page<PostResponse> getAllUserPosts(String username, String currentUserUsername, Pageable pageable){
        var postResponses =  postRepository.findByUser_UsernameOrderByTimePostedDesc(username, pageable).map(this::toPostResponseFromPost);
        return mapLiked(currentUserUsername, postResponses);
    }

    public Page<PostResponse> getUserLikes(String username, Pageable pageable) {
        return mapLiked(username, postRepository.findByUser_UsernameOrderByTimePostedDesc(username, pageable).map(this::toPostResponseFromPost));
    }
}
