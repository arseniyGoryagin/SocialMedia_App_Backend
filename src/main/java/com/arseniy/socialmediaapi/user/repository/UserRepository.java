package com.arseniy.socialmediaapi.user.repository;

import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.domain.projection.UserResponseProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


    @Query(value = "SELECT u.id, u.username, u.name, u.description, u.profile_picture, " +
            "(SELECT COUNT(*) FROM followers_table f1 WHERE f1.target = u.id) AS follower_count, " +
            "(SELECT COUNT(*) FROM followers_table f2 WHERE f2.follower = u.id) AS follows_count " +
            "FROM user_table u " +
            "WHERE u.username ILIKE '%' || :username || '%' " +
            "AND u.username <> :exclude",
            countQuery =
                    "Select count(*)" +
                    "from user_table u " +
                            "WHERE u.username ILIKE '%' || :username || '%' " +
                            "AND u.username <> :exclude",
            nativeQuery = true)
    Page<UserResponseProjection> searchUsers(@Param("username")String username,  @Param("exclude") String excludedUsername, Pageable page);

    @Query(value  = "Select u.id, u.username, u.name, u.description, u.profile_picture, " +
            "(Select Count(*) from followers_table f1 where f1.target = u.id) as follower_count, " +
            "(Select Count(*) from followers_table f2 where f2.follower = u.id) as follows_count " +
            "from user_table u " +
            "where u.username = :username ",
    nativeQuery = true)
    UserResponseProjection getUserAsResponse(@Param("username")String username);

}
