package com.arseniy.socialmediaapi.like;


import com.arseniy.socialmediaapi.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    /*@Query(
            value = "Select * " +
                    " from likes_table l " +
                    "inner join user_table u on u.id = l.user " +
                    "where u.username = :username ",
            countQuery = "Select count(*) " +
                    " from likes_table l " +
                    "inner join user_table u on u.id = l.user " +
                    "where u.username = :username ", nativeQuery = true)
    Page<Like> getUserLikes(@Param("username") String username, Pageable pageable);*/


    /*@Query(value = "Select count (*) " +
            "from likes_table l " +
            "inner join posts_table p on l.post = p.id " +
            " where p.id = :postId ",
    nativeQuery = true)
    Long getPostsLikeNumber(@Param("postId") Long post);*/

    Long countByPost_Id(Long post);

    Boolean existsByUser_UsernameAndPost_Id(String username, Long postId);

    Like findByUser_UsernameAndPost_Id(String username, Long postId);
}
