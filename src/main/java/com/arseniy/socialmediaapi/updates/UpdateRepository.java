package com.arseniy.socialmediaapi.updates;


import com.arseniy.socialmediaapi.updates.domain.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateRepository extends JpaRepository<Update, Long> {

    Page<Update> findByUserUsernameOrderByDateDesc(String username, Pageable pageable);

}
