package com.arseniy.socialmediaapi.user;

import com.arseniy.socialmediaapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String username);
    Page<User> findByUsernameStartingWith(String username, Pageable page);

    List<Long> findLikedPostIdsByUsername(String username);

    List<Long> findFollowsIdsByUsername(String username);

    Page<User> findFollowersByUsername(String username, Pageable pageable);
    Page<User> findFollowsByUsername(String username, Pageable pageable);
}
