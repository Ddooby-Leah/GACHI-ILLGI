package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.domain.repository.FollowRepository;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.FollowService;
import com.ddooby.gachiillgi.domain.service.FollowUserVO;
import com.ddooby.gachiillgi.domain.service.FollowUserVOList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void followUser(Long followUserId, Long followedUserId) {

        User followUser = userRepository.findById(followUserId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        User followedUser = userRepository.findById(followedUserId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        followRepository.save(
                Follow.builder()
                        .followedUser(followedUser)
                        .followUser(followUser)
                        .build()
        );
    }

    @Override
    @Transactional
    public void unfollowUser(Long followUserId, Long followedUserId) {
        User followUser = userRepository.findById(followUserId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        User followedUser = userRepository.findById(followedUserId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        followRepository.deleteByFollowUserAndFollowedUser(followUser, followedUser);
    }

    @Override
    @Transactional
    public FollowUserVOList getFollowers(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        return FollowUserVOList.builder()
                .list(followRepository.findByFollowedUser(user).stream()
                        .map(x -> FollowUserVO.builder()
                                .userId(x.getFollowUser().getUserId())
                                .name(x.getFollowUser().getName())
                                .sex(x.getFollowUser().getSex())
                                .nickname(x.getFollowUser().getNickname())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
