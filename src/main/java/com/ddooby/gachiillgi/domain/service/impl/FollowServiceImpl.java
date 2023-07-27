package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.exception.FollowErrorCodeEnum;
import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.domain.repository.FollowRepository;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.FollowService;
import com.ddooby.gachiillgi.domain.vo.FollowUserVO;
import com.ddooby.gachiillgi.domain.vo.FollowUserVOList;
import com.ddooby.gachiillgi.interfaces.dto.response.FollowResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public FollowResponseDTO followUser(String followerEmail, String follweeEmail) {

        // 팔로우하는 사람
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        // 팔로우 당하는 사람
        User followee = userRepository.findByEmail(follweeEmail)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowee(followee);

        Long id = followRepository.save(follow).getId();

        return FollowResponseDTO.builder()
                .id(id)
                .build();
    }

    @Override
    public FollowResponseDTO unfollowUser(String followerEmail, String follweeEmail) {
        User follower = userRepository.findByEmail(followerEmail)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        User followee = userRepository.findByEmail(follweeEmail)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new BizException(FollowErrorCodeEnum.FOLLOW_RELATION_NOT_FOUND));

        follow.setIsDelete(true);

        return FollowResponseDTO.builder()
                .id(follow.getId())
                .build();
    }

    @Override
    public FollowUserVOList getFollowers(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BizException(UserErrorCodeEnum.USER_NOT_FOUND));

        log.debug(user.toString());

        return FollowUserVOList.builder()
                .list(user.getFollowerList().stream()
                        .map(x -> FollowUserVO.builder()
                                .email(x.getFollower().getEmail())
                                .sex(x.getFollower().getSex())
                                .nickname(x.getFollower().getNickname())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
