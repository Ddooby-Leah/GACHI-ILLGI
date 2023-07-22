package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "userAuthoritySet")
    Optional<User> findOneWithUserAuthorityByEmail(String email);

    @EntityGraph(attributePaths = "userAuthoritySet")
    Optional<User> findOneWithUserAuthorityByUserId(Long userId);

    //    @Query("SELECT u FROM User u LEFT JOIN FETCH u.followerList WHERE u.userId = :userId")
    Optional<User> findOneWithFollowUsersByUserId(Long userId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
