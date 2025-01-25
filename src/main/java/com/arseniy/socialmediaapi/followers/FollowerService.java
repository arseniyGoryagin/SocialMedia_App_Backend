package com.arseniy.socialmediaapi.followers;


import com.arseniy.socialmediaapi.followers.domain.Follow;
import com.arseniy.socialmediaapi.followers.exceptions.FollowAlreadyExistsException;
import com.arseniy.socialmediaapi.followers.exceptions.FollowDoesNotExist;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    public void followUser(String targetUsername, String followerUsername) throws NoSuchUserException {

        User target = userRepository.findByUsername(targetUsername).orElseThrow(() -> new NoSuchUserException("No such user " + targetUsername));
        User follower = userRepository.findByUsername(followerUsername).orElseThrow(() -> new NoSuchUserException("No such user"));


        if(followerRepository.existsByTargetUsernameAndFollowerUsername(targetUsername, followerUsername)){
            throw new FollowAlreadyExistsException("The user is already following this user");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .target(target)
                .build();

        followerRepository.save(follow);
    }


    public void unFollowUser(String targetUsername, String followerUsername) throws  NoSuchUserException {


        User target = userRepository.findByUsername(targetUsername).orElseThrow(() -> new NoSuchUserException("No such user " + targetUsername));
        User follower = userRepository.findByUsername(followerUsername).orElseThrow(() -> new NoSuchUserException("No such user"));

        if(!followerRepository.existsByTargetUsernameAndFollowerUsername(targetUsername, followerUsername)){
            throw new FollowDoesNotExist("The user is not following");
        }

        followerRepository.deleteByFollowerIdAndTargetId(follower.getId(), target.getId());
    }
}
