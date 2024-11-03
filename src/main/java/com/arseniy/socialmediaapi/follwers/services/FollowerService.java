package com.arseniy.socialmediaapi.follwers.services;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.follwers.domain.model.Follow;
import com.arseniy.socialmediaapi.follwers.repository.FollowerRepository;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;


    public void followUser(String targetUsername, String followerUsername) throws UserException {

        Optional<User> target = userRepository.findByUsername(targetUsername);
        Optional<User> follower = userRepository.findByUsername(followerUsername);

        if(target.isEmpty()){
            throw new UserException("No such user to follow");
        }

        if(follower.isEmpty()){
            throw new UserException("No such who can follow");
        }


        if(followerRepository.isFollowing(targetUsername, followerUsername)){
            throw new UserException("You are already follwing this user");
        }


        Follow follow = Follow.builder()
                .follower(follower.get())
                .target(target.get())
                .build();

        var followRes = followerRepository.save(follow);

        log.info("Follow result " + followRes.toString());

    }


    public void unFollowUser(String targetUsername, String followerUsername) throws UserException {


        Optional<User> target = userRepository.findByUsername(targetUsername);
        Optional<User> follower = userRepository.findByUsername(followerUsername);

        if(target.isEmpty()){
            throw new UserException("No such user to unfollow");
        }

        if(follower.isEmpty()){
            throw new UserException("No such who can unfollow");
        }

        if(!followerRepository.isFollowing(targetUsername, followerUsername)){
            throw new UserException("This user is not followed by target");
        }

        followerRepository.deleteFollow(follower.get().getId(), target.get().getId());
    }
}
