package com.arseniy.socialmediaapi.like;


import com.arseniy.socialmediaapi.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Long countByPost_Id(Long post);

    Boolean existsByUser_UsernameAndPost_Id(String username, Long postId);

    Like findByUser_UsernameAndPost_Id(String username, Long postId);
}
