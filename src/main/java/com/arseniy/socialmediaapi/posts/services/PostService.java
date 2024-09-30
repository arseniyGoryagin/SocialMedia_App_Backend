package com.arseniy.socialmediaapi.posts.services;


import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {




    private final PostRepository postRepository;



    public List<Post> getPosts(Long limit, Long offset){
        return postRepository.getPosts(limit, offset);
    }

    public Optional<Post> getPost(Long id){
        return postRepository.findById(id);
    }


    public Post addPost(String title, String body, String username){
        Post post = Post
                .builder()
                .body(body)
                .title(title)
                .username(username)
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
        return postRepository.getUserPosts(username, limit, offset );
    }

}
