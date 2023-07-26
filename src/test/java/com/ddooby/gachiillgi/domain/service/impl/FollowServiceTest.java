package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.entity.Follow;
import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.domain.repository.FollowRepository;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class FollowServiceTest {

    @InjectMocks
    private FollowServiceImpl followService;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void 팔로우_유저_테스트() {
        // given
        String followerEmail = "ddooby.doobob@kakaoenterprise.com";
        String followeeEmail = "ddooby.doobob1@kakaoenterprise.com";

        // Create user objects for follower and followee
        User follower = User.builder()
                .sex("man")
                .email(followerEmail)
                .password("123")
                .birthday(LocalDate.of(2021, 1, 4))
                .nickname("ghghgh")
                .build();

        User followee = User.builder()
                .sex("man")
                .email(followeeEmail)
                .password("123")
                .birthday(LocalDate.of(2021, 1, 4))
                .nickname("ghghgh")
                .build();

        // Set up the mock to return the user objects when findByEmail is called
        when(userRepository.findByEmail(followerEmail)).thenReturn(Optional.of(follower));
        when(userRepository.findByEmail(followeeEmail)).thenReturn(Optional.of(followee));

        // when
        followService.followUser(followerEmail, followeeEmail);

        // then
        // followRepository.save() 메소드가 1번 호출되었는지 검증
        verify(followRepository, times(1)).save(any(Follow.class));
    }

    @Test
    public void 존재하지_않는_유저_팔로우_테스트() {
        // given
        String followerEmail = "ddooby.doobob@kakaoenterprise.com";
        String followeeEmail = "invalid-email@example.com";

        User follower = User.builder()
                .sex("man")
                .email(followerEmail)
                .password("123")
                .birthday(LocalDate.of(2021, 1, 4))
                .nickname("ghghgh")
                .build();

        // userRepository.findByEmail() 메소드에 대한 Mock 객체 생성
        when(userRepository.findByEmail(followerEmail)).thenReturn(Optional.of(follower));
        when(userRepository.findByEmail(followeeEmail)).thenReturn(Optional.empty());

        // when, then
        assertThrows(BizException.class, () -> followService.followUser(followerEmail, followeeEmail));
        verify(followRepository, never()).save(any(Follow.class));
    }

//    @Test
//    @DisplayName("언팔로우 테스트")
//    void 언팔로우_테스트() {
//
//        log.debug(String.valueOf(followRepository.findAll().size()));
//
//        User user1 = userRepository.save(
//                User.builder()
//                        .sex("man")
//                        .email("ddoobydoobob@email.com")
//                        .password("123")
//                        .birthday(LocalDate.of(2021, 1, 4))
//                        .nickname("ghghgh")
//                        .build()
//        );
//
//        User user2 = userRepository.save(
//                User.builder()
//                        .sex("man")
//                        .email("kakka@email.com")
//                        .password("123")
//                        .birthday(LocalDate.of(2021, 1, 4))
//                        .nickname("ghghgh")
//                        .build()
//        );
//
//        followService.followUser(user1.getUserId(), user2.getUserId());
//        followService.unfollowUser(user1.getUserId(), user2.getUserId());
//
//        Follow follow = followRepository.findByFollowerAndFollowee(user1, user2)
//                .orElseThrow(() -> new BizException(FollowErrorCodeEnum.FOLLOW_RELATION_NOT_FOUND));
//
//        assertThat(follow.getIsDelete()).isEqualTo(true);
//    }
//
//    @Test
//    void 내_팔로워_확인_테스트() {
//
//        User my = userRepository.save(
//                User.builder()
//                        .sex("man")
//                        .email("ddoobydoobob@email.com")
//                        .password("123")
//                        .birthday(LocalDate.of(2021, 1, 4))
//                        .nickname("ghghgh")
//                        .build()
//        );
//
//        User follower1 = userRepository.save(
//                User.builder()
//                        .sex("man")
//                        .email("kakka@email.com")
//                        .password("123")
//                        .birthday(LocalDate.of(2021, 1, 4))
//                        .nickname("ghghgh")
//                        .build()
//        );
//
//        User follower2 = userRepository.save(
//                User.builder()
//                        .sex("man")
//                        .email("kakkaaa@email.com")
//                        .password("123")
//                        .birthday(LocalDate.of(2021, 1, 4))
//                        .nickname("ghghgh")
//                        .build()
//        );
//
//        followService.followUser(follower1.getUserId(), my.getUserId());
//        followService.followUser(follower2.getUserId(), my.getUserId());
//        followService.followUser(my.getUserId(), follower2.getUserId());
//
//        log.debug("================================");
//        assertThat(followService.getFollowers(my.getUserId()).getCount()).isEqualTo(2);
//    }
}