package com.ddooby.gachiillgi.repository;

import com.ddooby.gachiillgi.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = "memberAuthoritySet")
    Optional<Member> findOneWithMemberAuthorityByUsername(String username);
}
