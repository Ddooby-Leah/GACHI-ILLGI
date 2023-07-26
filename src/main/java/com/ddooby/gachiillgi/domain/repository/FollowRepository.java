package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);

    boolean existsByFollowerAndFollowee(User follower, User followee);

}