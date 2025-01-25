package com.arseniy.socialmediaapi.posts;


import com.arseniy.socialmediaapi.posts.domain.Post;
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


    /*@Query(value =
            "Select * " +
                    "from posts_table p " +
                    "inner join follow_table f on p.user.id = f.target.id "+
                    "inner join user_table u on f.follower_id = u.id " +
                    "where u.username  = :username " +
                    "order by time_posted desc ",
            countQuery = "Select COUNT(*) " +
                    "from posts_table p " +
                    "inner join follow_table f on p.user.id = f.target.id "+
                    "inner join user_table u on f.follower_id = u.id " +
                    "where u.username  = :username",
            nativeQuery = true)
    Page<Post> getFeed(@Param("username") String username, Pageable pageable);*/


    Page<Post> findByUserIdIn(List<Long> ids, Pageable pageable);

    Page<Post> findByUserUsernameOrderByTimePostedDesc(String username, Pageable page);

    Page<Post> findByIdIn(List<Long> ids, Pageable pageable);

}
