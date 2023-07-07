package com.ddooby.gachiillgi.service;

import com.ddooby.gachiillgi.base.exception.DuplicateMemberException;
import com.ddooby.gachiillgi.base.exception.NotFoundMemberException;
import com.ddooby.gachiillgi.base.util.SecurityUtil;
import com.ddooby.gachiillgi.dto.UserDTO;
import com.ddooby.gachiillgi.entity.Authority;
import com.ddooby.gachiillgi.entity.User;
import com.ddooby.gachiillgi.entity.UserAuthority;
import com.ddooby.gachiillgi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO signup(UserDTO userDto) {
        if (userRepository.findOneWithUserAuthorityByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .activated(true)
                .build();

        user.setUserAuthoritySet(
                Collections.singleton(UserAuthority.builder()
                        .user(user)
                        .authority(authority)
                        .build())
        );

        user.getUserAuthoritySet().forEach(x -> log.debug(x.toString()));

        return UserDTO.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public UserDTO getUserWithAuthorities(String username) {
        return UserDTO.from(userRepository.findOneWithUserAuthorityByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDTO getMyUserWithAuthorities() {
        return UserDTO.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithUserAuthorityByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}
