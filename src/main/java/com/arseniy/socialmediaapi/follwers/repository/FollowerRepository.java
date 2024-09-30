package com.arseniy.socialmediaapi.follwers.repository;

import com.arseniy.socialmediaapi.follwers.domain.model.Follow;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follow, Long> {

    @Query(value = "Select * from followers_table where username = :username limit :limit offset :offset", nativeQuery = true)
    public List<Follow> getUserFollowers(@Param("username") String username, @Param("limit") Long limit, @Param("offset") Long offset);




    @Query(value = "Delete from followers_table where username = :username follower  = :follower", nativeQuery = true)
    public void unFollow (@Param("username") String username, @Param("follower") String follower);




}
