package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.UserRoleEnum;
import com.ddooby.gachiillgi.base.enums.UserStatusEnum;
import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.base.util.SecurityUtil;
import com.ddooby.gachiillgi.domain.entity.Authority;
import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.domain.entity.UserAuthority;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.UserService;
import com.ddooby.gachiillgi.interfaces.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDTO signup(UserDTO userDto) {
        if (userRepository.findOneWithUserAuthorityByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new BizException(UserErrorCodeEnum.DUPLICATE_USERNAME);
        }

        Authority authority = Authority.builder()
                .authorityName(UserRoleEnum.ROLE_USER.name())
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .activated(UserStatusEnum.PENDING)
                .build();

        user.setUserAuthoritySet(
                Collections.singleton(UserAuthority.builder()
                        .user(user)
                        .authority(authority)
                        .build())
        );

        return UserDTO.from(userRepository.save(user));
    }


    public UserDTO getUserWithAuthorities(String username) {
        return UserDTO.from(userRepository.findOneWithUserAuthorityByUsername(username).orElse(null));
    }


    public UserDTO getMyUserWithAuthorities() {
        return UserDTO.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithUserAuthorityByUsername)
                        .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND))
        );
    }
}
