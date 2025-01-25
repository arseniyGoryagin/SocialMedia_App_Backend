package com.arseniy.socialmediaapi.followers;

import com.arseniy.socialmediaapi.followers.domain.Follow;
import com.arseniy.socialmediaapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follow, Long> {

    public Page<User> findTargetByFollowerUsername(String username, Pageable page);
    public Page<User> findFollowerByTargetUsername(String username, Pageable page);
    public Boolean existsByTargetUsernameAndFollowerUsername(String target, String follower );

    public Long countByTargetUsername(String username);
    public Long countByFollowerUsername(String username);

    void deleteByFollowerIdAndTargetId(Long followerId, Long targetId);

}
