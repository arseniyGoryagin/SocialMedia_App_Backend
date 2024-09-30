package com.arseniy.socialmediaapi.follwers.services;


import com.arseniy.socialmediaapi.follwers.domain.model.Follow;
import com.arseniy.socialmediaapi.follwers.repository.FollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;

    public List<Follow> getUserFollowers(String username, Long limit, Long offset){

        return followerRepository.getUserFollowers(username , limit, offset);

    }

    public Follow followUser(String username, String followerUsername){

        Follow follow = Follow.builder()
                .follower(followerUsername)
                .username(username)
                .build();

        return followerRepository.save(follow);
    }


    public void unFollowUser(String username, String  followerUsername) {
        followerRepository.unFollow(username, followerUsername);
    }
}
