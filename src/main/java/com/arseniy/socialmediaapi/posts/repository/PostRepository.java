package com.arseniy.socialmediaapi.posts.repository;


import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.user.domain.model.User;
import jakarta.transaction.Transactional;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "Select * from posts_table limit :limit offset :offset", nativeQuery = true)
    List<Post> getPosts(@Param("limit") Long limit, @Param("offset") Long offset);



    @Modifying
    @Transactional
    @Query(value = "UPDATE posts_table set body = :newBody where id = :id", nativeQuery = true)
    void editPost(@Param("id")Long id, @Param("newBody")String newBody);

    @Query(value = "Select p.*, u.id as users_id from posts_table p inner join user_table u on p.user_id = u.id where u.username =:username  limit :limit offset :offset",
            nativeQuery = true)
    List<Post> getUserPosts(@Param("username")String username, @PathParam("limit") Long limit, @PathParam("offset") Long offset);

}
