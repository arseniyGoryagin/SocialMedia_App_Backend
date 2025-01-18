package com.arseniy.socialmediaapi.followers;

import com.arseniy.socialmediaapi.followers.domain.Follow;
import com.arseniy.socialmediaapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follow, Long> {


    /*@Query(value = "Select u.id, u.username, u.name, u.description, u.profile_picture, " +
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
    public Page<UserResponseProjection> getUserFollowers(@Param("username") String username, Pageable page);*/


    /*@Query(value = "Select u.id, u.username, u.name, u.description, u.profile_picture, " +
            "(Select count(*) from followers_table f2 where f2.target = u.id) as follower_count, " +
            "(Select count(*) from followers_table f3 where f3.follower = u.id) as follows_count " +
            "from user_table u " +
            "inner join followers_table f on u.id = f.follower " +
            "where f.target = (SELECT u2.id FROM user_table u2 WHERE u2.username = :username) ",
            nativeQuery = true

    )
    public List<UserResponseProjection> getUserFollowersList(@Param("username") String username, Pageable page);*/



    /*@Query(value = "Select u.id, u.username, u.name, u.description, u.profile_picture, " +
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
    public Page<UserResponseProjection> getUserFollows(@Param("username") String username, Pageable page);*/

    public Page<User> findTargetByFollower_Username(String username, Pageable page);
    public Page<User> findFollowerByTarget_Username(String username, Pageable page);
    public Boolean existsByTarget_UsernameAndFollower_Username(String target, String follower );

    public Long countByTarget_Username(String username);
    public Long countByFollower_Username(String username);

    void deleteByFollower_IdAndTarget_Id(Long followerId, Long targetId);

}




  /*@Query(value = "Select count(*) > 0 " +
            "from followers_table f " +
            "inner join user_table target_user on f.target  = target_user.id " +
            "inner join user_table followers_user on f.follower = followers_user.id " +
            "where target_user.username = :target and followers_user.username = :follower",
            nativeQuery = true
    )
    public Boolean isFollowing(@Param("target") String  targetUsername, @Param("follower") String followerUsername);*/
