package com.arseniy.socialmediaapi.updates.repository;


import com.arseniy.socialmediaapi.updates.domain.model.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateRepository extends JpaRepository<Update, Long> {

    @Query(value = "Select * from updates_table where username = :username limit :limit offset :offset", nativeQuery = true)
    List<Update> getAllUserUpdates(@Param("username") String username, @Param("limit") Long limit, @Param("offset") Long offset);

}
