package com.ddooby.gachiillgi.service;

import com.ddooby.gachiillgi.base.exception.DuplicateMemberException;
import com.ddooby.gachiillgi.base.exception.NotFoundMemberException;
import com.ddooby.gachiillgi.base.util.SecurityUtil;
import com.ddooby.gachiillgi.dto.MemberDTO;
import com.ddooby.gachiillgi.entity.Authority;
import com.ddooby.gachiillgi.entity.Member;
import com.ddooby.gachiillgi.entity.MemberAuthority;
import com.ddooby.gachiillgi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDTO signup(MemberDTO memberDto) {
        if (memberRepository.findOneWithMemberAuthorityByUsername(memberDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .nickname(memberDto.getNickname())
                .activated(true)
                .build();

        member.setMemberAuthoritySet(
                Collections.singletonList(MemberAuthority.builder()
                        .member(member)
                        .authority(authority)
                        .build())
        );

        member.getMemberAuthoritySet().forEach(x -> log.debug(x.toString()));

        return MemberDTO.from(memberRepository.save(member));
    }

    @Transactional(readOnly = true)
    public MemberDTO getUserWithAuthorities(String username) {
        return MemberDTO.from(memberRepository.findOneWithMemberAuthorityByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public MemberDTO getMyUserWithAuthorities() {
        return MemberDTO.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(memberRepository::findOneWithMemberAuthorityByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}
