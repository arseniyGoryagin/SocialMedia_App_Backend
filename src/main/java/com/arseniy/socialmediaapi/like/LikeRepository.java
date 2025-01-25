package com.arseniy.socialmediaapi.like;


import com.arseniy.socialmediaapi.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Long countByPostId(Long post);

    Boolean existsByUserUsernameAndPostId(String username, Long postId);

    Optional<Like> findByUserUsernameAndPostId(String username, Long postId);
}
