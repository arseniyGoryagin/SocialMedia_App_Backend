package com.arseniy.socialmediaapi.like.services;


import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    public List<Like> getUserLikes(String username, Long limit, Long offset){
        return likeRepository.getUserLikes(username, limit,  offset);
    }

    public  void  likePost(String username, Long postId){

        Like like = Like.builder()
                .postId(postId)
                .username(username)
                .build();

         likeRepository.save(like);
    }


    public void unlikePost(String username, Long postId){

        Like like = likeRepository.findByUsernameAndPostId(username, postId);

        likeRepository.delete(like);
    }


}
