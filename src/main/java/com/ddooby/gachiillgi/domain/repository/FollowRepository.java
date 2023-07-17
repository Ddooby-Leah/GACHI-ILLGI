package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByFollowUserAndFollowedUser(User followUser, User followedUser);

    boolean existsByFollowUserAndFollowedUser(User followUser, User followedUser);

//    @Query("SELECT f FROM Follow f LEFT JOIN FETCH f.followedUser WHERE f.followedUser = :followedUser")
    List<Follow> findByFollowedUser(User followedUser);


}