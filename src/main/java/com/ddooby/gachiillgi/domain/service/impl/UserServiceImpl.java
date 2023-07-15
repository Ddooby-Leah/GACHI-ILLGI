package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.UserRoleEnum;
import com.ddooby.gachiillgi.base.enums.UserStatusEnum;
import com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum;
import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.base.util.SecurityUtil;
import com.ddooby.gachiillgi.domain.entity.Authority;
import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.domain.entity.UserAuthority;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.UserService;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRequestDTO;
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


    public UserRequestDTO signup(UserRequestDTO userRequestDto) {
        if (userRepository.findOneWithUserAuthorityByUsername(userRequestDto.getUsername()).orElse(null) != null) {
            throw new BizException(UserErrorCodeEnum.DUPLICATE_USERNAME);
        }

        Authority authority = Authority.builder()
                .authorityName(UserRoleEnum.ROLE_USER.name())
                .build();

        User user = User.builder()
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .nickname(userRequestDto.getNickname())
                .activated(UserStatusEnum.PENDING)
                .build();

        user.setUserAuthoritySet(
                Collections.singleton(UserAuthority.builder()
                        .user(user)
                        .authority(authority)
                        .build())
        );

        return UserRequestDTO.from(userRepository.save(user));
    }

    @Override
    public void updateActivated(String username) {
        User user = userRepository.findOneWithUserAuthorityByUsername(username)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        if (user.getActivated() == UserStatusEnum.ACTIVATED) {
            throw new BizException(AuthErrorCodeEnum.ALEADY_COMPLETE_VERIFICATION);
        } else {
            user.setActivated(UserStatusEnum.ACTIVATED);
        }
    }

    public UserRequestDTO getUserWithAuthorities(String username) {
        return UserRequestDTO.from(userRepository.findOneWithUserAuthorityByUsername(username).orElse(null));
    }

    public UserRequestDTO getMyUserWithAuthorities() {
        return UserRequestDTO.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithUserAuthorityByUsername)
                        .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND))
        );
    }
}
