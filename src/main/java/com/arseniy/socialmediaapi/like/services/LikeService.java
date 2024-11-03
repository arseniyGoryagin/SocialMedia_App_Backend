package com.arseniy.socialmediaapi.like.services;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.like.repository.LikeRepository;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.posts.repository.PostRepository;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public  void  likePost(String username, Long postId) throws UserException {


        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty()){
            throw new UserException("No such post!");
        }

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            throw new UserException("No such user!");
        }

        Like like = Like.builder()
                .post(post.get())
                .user(user.get())
                .build();


        log.info("Liking post");
         likeRepository.save(like);
    }


    public void unlikePost(String username, Long postId){
        Like like = likeRepository.findByUser_UsernameAndPost_Id(username, postId);
        log.info("Unliking post");
        likeRepository.delete(like);
    }


}
