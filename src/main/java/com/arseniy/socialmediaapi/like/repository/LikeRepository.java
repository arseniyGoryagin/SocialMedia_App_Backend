package com.arseniy.socialmediaapi.like.repository;


import com.arseniy.socialmediaapi.like.domain.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "Select * from likes_table where username = :username limit :limit offset :offset", nativeQuery = true)
    List<Like> getUserLikes(@Param("username") String username, @Param("limit") Long limit, @Param("offset") Long offset);



    Like findByUsernameAndPostId(String username, Long postId);
}
