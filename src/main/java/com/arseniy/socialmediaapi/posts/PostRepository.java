package com.arseniy.socialmediaapi.posts;


import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUserUsernameOrderByTimePostedDesc(String username, Pageable page);
    Page<Post> findByLiked(User user, Pageable pageable);

    Page<Post> findByIdIn(List<Long> ids, Pageable pageable);

    boolean existsByLiked(User user);

}
