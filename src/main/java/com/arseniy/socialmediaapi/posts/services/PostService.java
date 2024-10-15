package com.arseniy.socialmediaapi.posts.services;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.posts.repository.PostRepository;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {



    private final UserRepository userRepository;
    private final PostRepository postRepository;



    public List<Post> getPosts(Long limit, Long offset){
        return postRepository.getPosts(limit, offset);
    }

    public Optional<Post> getPost(Long id){
        return postRepository.findById(id);
    }


    public Post addPost(String body, User user) throws UserException {


        Post post = Post
                .builder()
                .edited(false)
                .body(body)
                .user(user)
                .likes(0L)
                .build();
        postRepository.save(post);
        return post;
    }


    public void removePost(Long id){
        postRepository.deleteAllById(Collections.singleton(id));
    }

    public void editPost(Long postId, String newBody) {
        postRepository.editPost(postId, newBody);
    }

    public List<Post> getAllUserPosts(String username, Long limit, Long offset){
        log.info("username = " + username);
        return postRepository.getUserPosts(username, limit, offset );
    }

}
