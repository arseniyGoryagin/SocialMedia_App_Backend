package com.arseniy.socialmediaapi.like;


import com.arseniy.socialmediaapi.like.exceptions.PostNotLikedByUser;
import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.posts.PostRepository;
import com.arseniy.socialmediaapi.posts.exceptions.NoSuchPostException;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public  void  likePost(String currentUserUsername, Long postId) throws NoSuchPostException, NoSuchUserException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchPostException("This post does not exist"));
        User user = userRepository.findByUsername(currentUserUsername).orElseThrow(() -> new NoSuchUserException("No such user"));
        user.getLikes().add(post);
    }


    public void unlikePost(String currentUserUsername, Long postId){
        User user = userRepository.findByUsername(currentUserUsername).orElseThrow(() -> new NoSuchUserException("No such user"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchPostException("This post does not exist"));
        user.getLikes().remove(post);
    }


}
