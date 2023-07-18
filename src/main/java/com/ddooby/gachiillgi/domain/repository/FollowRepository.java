package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByFollowerAndFollowee(User follower, User followee);

    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);

    boolean existsByFollowerAndFollowee(User follower, User followee);

//    @Query("SELECT f FROM Follow f LEFT JOIN FETCH f.followedUser WHERE f.followedUser = :followedUser")
    List<Follow> findByFollowee(User followedUser);


}