package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.exception.FollowErrorCodeEnum;
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
    public void followUser(Long followerId, Long followeeId) {

        // 팔로우하는 사람
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        // 팔로우 당하는 사람
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        Follow follow = Follow.builder().follower(follower).followee(followee).build();

        follow.setFollower(follower);
        follow.setFollowee(followee);

        followRepository.save(follow);
    }

    @Override
    @Transactional
    public void unfollowUser(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new BizException(FollowErrorCodeEnum.FOLLOW_RELATION_NOT_FOUND));

        follow.setIsDelete(true);
    }

    @Override
    @Transactional
    public FollowUserVOList getFollowers(Long userId) {

        User user = userRepository.findOneWithFollowUsersByUserId(userId)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        log.debug(user.toString());

        return FollowUserVOList.builder()
                .list(user.getFollowerList().stream()
                        .map(x -> FollowUserVO.builder()
                        .userId(x.getFollower().getUserId())
                        .name(x.getFollower().getName())
                        .sex(x.getFollower().getSex())
                        .nickname(x.getFollower().getNickname())
                        .build())
                    .collect(Collectors.toList())
                )
                .build();
    }
}
