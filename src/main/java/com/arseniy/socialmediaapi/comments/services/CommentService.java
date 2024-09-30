package com.arseniy.socialmediaapi.comments.services;


import com.arseniy.socialmediaapi.comments.domain.model.Comment;
import com.arseniy.socialmediaapi.comments.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;




    public List<Comment> getPostsComments(Long postId, Long limit, Long offset){
        return commentRepository.getPostsComments(postId, limit, offset);
    }

    public Optional<Comment> getComment(Long comId){
        return commentRepository.findById(comId);
    }


    public Comment addComment(String username, Long postId, String body){
        Comment com = Comment.builder().body(body).postId(postId).likes(0L).username(username).build();
        return commentRepository.save(com);
    }


    public void deleteComment(Long comId){
        commentRepository.deleteById(comId);
    }


    public void editComment(Long comId, String newBody){
        commentRepository.editComment(comId, newBody);
    }



    // TODO liking


}
