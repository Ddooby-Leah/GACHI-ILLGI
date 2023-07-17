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
import com.ddooby.gachiillgi.domain.repository.AuthorityRepository;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.UserService;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;


    public UserRequestDTO signup(UserRequestDTO userRequestDto) {
        if (userRepository.findOneWithUserAuthorityByEmail(userRequestDto.getEmail()).orElse(null) != null) {
            throw new BizException(UserErrorCodeEnum.DUPLICATE_EMAIL);
        }

        Authority authority = authorityRepository.findByAuthorityName(UserRoleEnum.ROLE_USER.name());

        User user = User.builder()
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .nickname(userRequestDto.getNickname())
                .name(userRequestDto.getName())
                .sex(userRequestDto.getSex())
                .birthday(userRequestDto.getBirthday())
                .activated(UserStatusEnum.PENDING)
                .build();

        user.setUserAuthoritySet(
                Collections.singleton(UserAuthority.builder()
                        .user(user)
                        .authority(authority)
                        .build())
        );

        return UserRequestDTO.from(
                userRepository.save(user)
        );
    }

    @Override
    public void updateActivated(String email) {
        User user = userRepository.findOneWithUserAuthorityByEmail(email)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        if (user.getActivated() == UserStatusEnum.ACTIVATED) {
            throw new BizException(AuthErrorCodeEnum.ALEADY_COMPLETE_VERIFICATION);
        } else {
            user.setActivated(UserStatusEnum.ACTIVATED);
        }
    }

    public UserRequestDTO getUserWithAuthorities(String email) {
        return UserRequestDTO.from(userRepository.findOneWithUserAuthorityByEmail(email).orElse(null));
    }

    public UserRequestDTO getMyUserWithAuthorities() {
        return UserRequestDTO.from(
                SecurityUtil.getCurrentUserEmail()
                        .flatMap(userRepository::findOneWithUserAuthorityByEmail)
                        .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND))
        );
    }
}
