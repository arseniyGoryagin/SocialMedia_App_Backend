package com.arseniy.socialmediaapi.follwers.repository;

import com.arseniy.socialmediaapi.follwers.domain.model.Follow;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.arseniy.socialmediaapi.user.domain.projection.*;
import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follow, Long> {

    /*

     private Long id;
    private  String username;
    private  String name;
    private  String description;
    private  String profilePicture;

    private Long followerCount;
    private Long followsCount;

    */


    @Query(value = "Select u.id, u.username, u.name, u.description, u.profile_picture, " +
            "(Select count(*) from followers_table f2 where f2.target = u.id) as follower_count, " +
            "(Select count(*) from followers_table f3 where f3.follower = u.id) as follows_count " +
            "from user_table u " +
            "inner join followers_table f on u.id = f.follower " +
            "where f.target = (SELECT u2.id FROM user_table u2 WHERE u2.username = :username) ",
            countQuery = "Select COUNT(*) " +
                    "from user_table u " +
                    "inner join followers_table f on u.id = f.follower " +
                    "where f.target = (SELECT u2.id FROM user_table u2 WHERE u2.username = :username) ",
            nativeQuery = true

    )
    public Page<UserResponseProjection> getUserFollowers(@Param("username") String username, Pageable page);


    @Query(value = "Select u.id, u.username, u.name, u.description, u.profile_picture, " +
            "(Select count(*) from followers_table f2 where f2.target = u.id) as follower_count, " +
            "(Select count(*) from followers_table f3 where f3.follower = u.id) as follows_count " +
            "from user_table u " +
            "inner join followers_table f on u.id = f.follower " +
            "where f.target = (SELECT u2.id FROM user_table u2 WHERE u2.username = :username) ",
            nativeQuery = true

    )
    public List<UserResponseProjection> getUserFollowersList(@Param("username") String username, Pageable page);



    @Query(value = "Select u.id, u.username, u.name, u.description, u.profile_picture, " +
            "(Select count(*) from followers_table f2 where f2.target = u.id) as follower_count, " +
            "(Select count(*) from followers_table f3 where f3.follower = u.id) as follows_count " +
            "from user_table u " +
            "inner join followers_table f on u.id = f.follower " +
            "where f.follower = (SELECT u2.id FROM user_table u2 WHERE u2.username = :username) ",
            countQuery = "Select count(*) " +
                    "from user_table u " +
                    "inner join followers_table f on u.id = f.target " +
                    "where f.follower = (SELECT u2.id FROM user_table u2 WHERE u2.username = :username)",
            nativeQuery = true
    )
    public Page<UserResponseProjection> getUserFollows(@Param("username") String username, Pageable page);


    @Query(value = "Select count(*) > 0 " +
            "from followers_table f " +
            "inner join user_table target_user on f.target  = target_user.id " +
            "inner join user_table followers_user on f.follower = followers_user.id " +
            "where target_user.username = :target and followers_user.username = :follower",
            nativeQuery = true
    )
    public Boolean isFollowing(@Param("target") String  targetUsername, @Param("follower") String followerUsername);


    @Transactional
    @Modifying
    @Query(value = "Delete from followers_table  where follower = :follower and followed = :followed", nativeQuery = true)
    void deleteFollow(@Param("follower") Long followerId, @Param("followed") Long followedId);


}
