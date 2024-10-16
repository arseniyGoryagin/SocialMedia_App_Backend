package com.arseniy.socialmediaapi.user.repository;

import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
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



    @Query("select new com.arseniy.socialmediaapi.user.domain.dto.UserResponse(u.id, u.username, u.description, u.profilePicture) from User u where u.username ILIKE %:username% and u.username <> :exclude")
    Page<UserResponse> searchUsers(@Param("username")String username,  @Param("exclude") String excludedUsername, Pageable page);

}
