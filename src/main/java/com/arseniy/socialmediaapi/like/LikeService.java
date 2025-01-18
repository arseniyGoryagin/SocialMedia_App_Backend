package com.arseniy.socialmediaapi.like;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.like.domain.Like;
import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.posts.PostRepository;
import com.arseniy.socialmediaapi.updates.domain.Update;
import com.arseniy.socialmediaapi.updates.UpdateService;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    private final UpdateService updateService;


    public  void  likePost(String currentUserUsername, Long postId) throws EmailAlreadyInUseException, NoSuchException {


        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchException("No such user"));
        User user = userRepository.findByUsername(currentUserUsername).orElseThrow(() -> new NoSuchException("No such user"));


        Like like = Like.builder()
                .post(post)
                .user(user)
                .build();


        log.info("Liking post");
        likeRepository.save(like);

        if(!Objects.equals(post.getUser().getUsername(), currentUserUsername)) {
            updateService.makeUpdate(post.getUser().getUsername(), currentUserUsername, currentUserUsername + " liked your post", Update.Type.Like);
        }

    }


    public void unlikePost(String username, Long postId){
        Like like = likeRepository.findByUser_UsernameAndPost_Id(username, postId);
        log.info("Unliking post");
        likeRepository.delete(like);
    }


}
