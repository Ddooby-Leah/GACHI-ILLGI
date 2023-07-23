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
import com.ddooby.gachiillgi.interfaces.dto.request.UserDetailInfoRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.UserRegisterResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;


    public UserRegisterResponseDTO signup(UserRegisterRequestDTO userRegisterRequestDto) {
        if (userRepository.findOneWithUserAuthorityByEmail(userRegisterRequestDto.getEmail()).orElse(null) != null) {
            throw new BizException(UserErrorCodeEnum.DUPLICATE_EMAIL);
        }

        Authority authority = authorityRepository.findByAuthorityName(UserRoleEnum.ROLE_USER.name());

        User user = User.builder()
                .email(userRegisterRequestDto.getEmail())
                .password(passwordEncoder.encode(userRegisterRequestDto.getPassword()))
                .nickname(userRegisterRequestDto.getNickname())
                .sex(userRegisterRequestDto.getSex())
                .birthday(userRegisterRequestDto.getBirthday())
                .activated(UserStatusEnum.PENDING)
                .isOAuthUser(userRegisterRequestDto.getIsOAuthUser())
                .build();

        user.setUserAuthoritySet(
                Collections.singleton(UserAuthority.builder()
                        .user(user)
                        .authority(authority)
                        .build())
        );

        return UserRegisterResponseDTO.from(
                userRepository.save(user)
        );
    }

    @Override
    public UserRegisterResponseDTO signupWithDetail(UserDetailInfoRegisterRequestDTO requestDTO) {
        User tempUserInfo = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        if (tempUserInfo.getActivated() == UserStatusEnum.ACTIVATED) {
            throw new BizException(AuthErrorCodeEnum.ALREADY_COMPLETE_ADD_DETAIL);
        }
        tempUserInfo.setNickname(requestDTO.getNickname());
        tempUserInfo.setBirthday(requestDTO.getBirthday());
        tempUserInfo.setSex(requestDTO.getSex());
        tempUserInfo.setActivated(UserStatusEnum.ACTIVATED);

        return UserRegisterResponseDTO.from(tempUserInfo);
    }

    @Override
    public void updateActivated(String email) {
        User user = userRepository.findOneWithUserAuthorityByEmail(email)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        if (user.getActivated() == UserStatusEnum.ACTIVATED) {
            throw new BizException(AuthErrorCodeEnum.ALREADY_COMPLETE_VERIFICATION);
        } else {
            user.setActivated(UserStatusEnum.ACTIVATED);
        }
    }

    @Override
    public UserRegisterRequestDTO getUserWithAuthorities(String email) {
        return UserRegisterRequestDTO.from(userRepository.findOneWithUserAuthorityByEmail(email).orElse(null));
    }

    @Override
    public UserRegisterRequestDTO getMyUserWithAuthorities() {
        return UserRegisterRequestDTO.from(
                SecurityUtil.getCurrentUserEmail()
                        .flatMap(userRepository::findOneWithUserAuthorityByEmail)
                        .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND))
        );
    }

    @Override
    public boolean isUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && isActivatedUser(user.get());
    }

    private boolean isActivatedUser(User user) {
        return user.getActivated() == UserStatusEnum.ACTIVATED;
    }
}
