package com.arseniy.socialmediaapi.comments;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost_Id(Long postId, Pageable pageable);

    Long countByPost_Id(Long postId);

    @Modifying
    @Transactional
    @Query(value = "Update comments_table set edited = true  body = :newBody where id = :comId", nativeQuery = true)
    void  editComment(@Param("comId") Long id, @Param("newBody") String newBody);

}
