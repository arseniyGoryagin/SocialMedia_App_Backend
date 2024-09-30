package com.arseniy.socialmediaapi.comments.repositories;


import com.arseniy.socialmediaapi.comments.domain.model.Comment;
import com.arseniy.socialmediaapi.user.domain.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "Select * from comments_table where postId = :postId limit :limit offset :offset", nativeQuery = true)
    List<Comment> getPostsComments(@Param("postId") Long postId, @Param("limit") Long limit, @Param("offset") Long offset);


    @Modifying
    @Transactional
    @Query(value = "Update comments_table set edited = true  body = :newBody where id = :comId", nativeQuery = true)
    void  editComment(@Param("comId") Long id, @Param("newBody") String newBody);

}
