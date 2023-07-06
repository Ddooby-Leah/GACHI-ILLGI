package com.ddooby.gachiillgi.service;

import com.ddooby.gachiillgi.entity.Member;
import com.ddooby.gachiillgi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        return memberRepository.findOneWithMemberAuthorityByUsername(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private User createUser(String username, Member member) {
        if (!member.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = member.getMemberAuthoritySet().stream()
                .map(memberAuthority -> new SimpleGrantedAuthority(memberAuthority.getAuthority().getAuthorityName()))
                .collect(Collectors.toList());

        log.debug(member.toString());
        log.debug(grantedAuthorities.toString());

        return new User(
                member.getUsername(),
                member.getPassword(),
                grantedAuthorities);
    }
}
