package com.arseniy.socialmediaapi.followers;

import com.arseniy.socialmediaapi.followers.domain.Follow;
import com.arseniy.socialmediaapi.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follow, Long> {

    public Page<User> findTargetByFollower_Username(String username, Pageable page);
    public Page<User> findFollowerByTarget_Username(String username, Pageable page);
    public Boolean existsByTarget_UsernameAndFollower_Username(String target, String follower );

    public Long countByTarget_Username(String username);
    public Long countByFollower_Username(String username);

    void deleteByFollower_IdAndTarget_Id(Long followerId, Long targetId);

}
