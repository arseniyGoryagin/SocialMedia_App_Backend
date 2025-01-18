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


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value =
            "Select * " +
            "from posts_table " +
            "order by time_posted desc",
            countQuery = "Select Count(*) from posts_table",
            nativeQuery = true)
    Page<Post> getPosts(Pageable pageable);

    @Query(value =
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
    Page<Post> getFeed(@Param("username") String username, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts_table set body = :newBody where id = :id", nativeQuery = true)
    void editPost(@Param("id")Long id, @Param("newBody")String newBody);

    /*
    @Query(value =
            "Select p.*  from posts_table p " +
            " inner join user_table u on p.user_id = u.id " +
            "where u.username =:username " +
            "order by p.time_posted desc",
            countQuery =
                    "Select count(*) " +
                    "from posts_table p inner join user_table u on p.user_id = u.id " +
                    " where u.username = :username ",
            nativeQuery = true)
    Page<Post> getUserPosts(@Param("username")String username, Pageable pageable);*/


    Page<Post> findByUser_UsernameOrderByTimePostedDesc(String username, Pageable page);

    Page<Post> findByLikes_User_Username(String username, Pageable pageable);

}
