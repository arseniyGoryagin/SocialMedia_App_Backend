package com.arseniy.socialmediaapi.followers;

import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final UserRepository userRepository;

    public void followUser(String targetUsername, String followerUsername) throws NoSuchUserException {
        User target = userRepository.findByUsername(targetUsername).orElseThrow(() -> new NoSuchUserException("No such user " + targetUsername));
        User follower = userRepository.findByUsername(followerUsername).orElseThrow(() -> new NoSuchUserException("No such user"));
        target.getFollowers().add(follower);
    }


    public void unFollowUser(String targetUsername, String followerUsername) throws  NoSuchUserException {
        User target = userRepository.findByUsername(targetUsername).orElseThrow(() -> new NoSuchUserException("No such user " + targetUsername));
        User follower = userRepository.findByUsername(followerUsername).orElseThrow(() -> new NoSuchUserException("No such user"));
        target.getFollowers().remove(follower);
    }
}
