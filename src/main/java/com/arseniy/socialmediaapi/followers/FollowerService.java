package com.arseniy.socialmediaapi.followers;


import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.followers.domain.Follow;
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
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    private final UpdateService updateService;


    public void followUser(String targetUsername, String followerUsername) throws NoSuchException {

        User target = userRepository.findByUsername(targetUsername).orElseThrow(() -> new NoSuchException("No such user " + targetUsername));
        User follower = userRepository.findByUsername(followerUsername).orElseThrow(() -> new NoSuchException("No such user"));


        if(followerRepository.existsByTarget_UsernameAndFollower_Username(targetUsername, followerUsername)){
            throw new NoSuchException("The user is already following this user");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .target(target)
                .build();

        followerRepository.save(follow);

        if(!Objects.equals(targetUsername, followerUsername)){
            updateService.makeUpdate(targetUsername, followerUsername, followerUsername + " followed you", Update.Type.Follow);
        }

    }


    public void unFollowUser(String targetUsername, String followerUsername) throws  NoSuchException {


        User target = userRepository.findByUsername(targetUsername).orElseThrow(() -> new NoSuchException("No such user " + targetUsername));
        User follower = userRepository.findByUsername(followerUsername).orElseThrow(() -> new NoSuchException("No such user"));

        if(!followerRepository.existsByTarget_UsernameAndFollower_Username(targetUsername, followerUsername)){
            throw new NoSuchException("The user is not following");
        }

        followerRepository.deleteByFollower_IdAndTarget_Id(follower.getId(), target.getId());
    }
}
