package com.ddooby.gachiillgi.repository;

import com.ddooby.gachiillgi.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "userAuthoritySet")
    Optional<User> findOneWithUserAuthorityByUsername(String username);
}
