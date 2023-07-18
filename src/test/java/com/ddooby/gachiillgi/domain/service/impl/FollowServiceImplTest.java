package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.exception.FollowErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.domain.repository.FollowRepository;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class FollowServiceImplTest {

    @Autowired
    private FollowService followService;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("팔로우 테스트")
    void 팔로우_테스트() {
        User user1 = userRepository.save(
                User.builder()
                        .sex("man")
                        .email("ddoobydoobob@email.com")
                        .password("123")
                        .name("김뚜비")
                        .birthday(LocalDate.of(2021, 1, 4))
                        .nickname("ghghgh")
                        .build()
        );

        User user2 = userRepository.save(
                User.builder()
                        .sex("man")
                        .email("kakka@email.com")
                        .password("123")
                        .name("김뚜비")
                        .birthday(LocalDate.of(2021, 1, 4))
                        .nickname("ghghgh")
                        .build()
        );

        followService.followUser(user1.getUserId(), user2.getUserId());
        assertThat(followRepository.existsByFollowerAndFollowee(user1, user2)).isEqualTo(true);
    }

    @Test
    @DisplayName("언팔로우 테스트")
    void 언팔로우_테스트() {

        log.debug(String.valueOf(followRepository.findAll().size()));

        User user1 = userRepository.save(
                User.builder()
                    .sex("man")
                    .email("ddoobydoobob@email.com")
                    .password("123")
                    .name("김뚜비")
                    .birthday(LocalDate.of(2021, 1, 4))
                    .nickname("ghghgh")
                    .build()
        );

        User user2 = userRepository.save(
                User.builder()
                        .sex("man")
                        .email("kakka@email.com")
                        .password("123")
                        .name("김뚜비")
                        .birthday(LocalDate.of(2021, 1, 4))
                        .nickname("ghghgh")
                        .build()
        );

        followService.followUser(user1.getUserId(), user2.getUserId());
        followService.unfollowUser(user1.getUserId(), user2.getUserId());

        Follow follow = followRepository.findByFollowerAndFollowee(user1, user2)
                .orElseThrow(() -> new BizException(FollowErrorCodeEnum.FOLLOW_RELATION_NOT_FOUND));

        assertThat(follow.getIsDelete()).isEqualTo(true);
    }

    @Test
    void 내_팔로워_확인_테스트() {

        User my = userRepository.save(
                User.builder()
                        .sex("man")
                        .email("ddoobydoobob@email.com")
                        .password("123")
                        .name("김뚜비")
                        .birthday(LocalDate.of(2021, 1, 4))
                        .nickname("ghghgh")
                        .build()
        );

        User follower1 = userRepository.save(
                User.builder()
                        .sex("man")
                        .email("kakka@email.com")
                        .password("123")
                        .name("김뚜비")
                        .birthday(LocalDate.of(2021, 1, 4))
                        .nickname("ghghgh")
                        .build()
        );

        User follower2 = userRepository.save(
                User.builder()
                        .sex("man")
                        .email("kakkaaa@email.com")
                        .password("123")
                        .name("김뚜비")
                        .birthday(LocalDate.of(2021, 1, 4))
                        .nickname("ghghgh")
                        .build()
        );

        followService.followUser(follower1.getUserId(), my.getUserId());
        followService.followUser(follower2.getUserId(), my.getUserId());
        followService.followUser(my.getUserId(), follower2.getUserId());

        log.debug("================================");
        assertThat(followService.getFollowers(my.getUserId()).getCount()).isEqualTo(2);
    }
}