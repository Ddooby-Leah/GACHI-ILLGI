package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.base.enums.exception.FollowErrorCodeEnum;
import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.service.impl.FollowServiceImpl;
import com.ddooby.gachiillgi.domain.vo.FollowUserVOList;
import com.ddooby.gachiillgi.interfaces.dto.response.FollowResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@Slf4j
@SpringBootTest
@SqlGroup(
        {
                @Sql(value = "/sql/follow-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        }
)
class FollowServiceTest {

    @Autowired
    private FollowServiceImpl followService;

    @Test
    void 유저_이메일_2개로_팔로우관계를_만들_수_있다() {
        // given
        String followerEmail = "ddooby.doobob1@kakaoenterprise.com";
        String followeeEmail = "ddooby.doobob@kakaoenterprise.com";

        // when
        FollowResponseDTO followResponseDTO = followService.followUser(followerEmail, followeeEmail);

        // then
        Assertions.assertThat(followResponseDTO.getId()).isNotNull();
    }

    @Test
    void 존재하지_않는_유저_팔로우_관계를_만들려고하면_USER_NOT_FOUND_예외가_발생한다() {
        // given
        String followerEmail = "ddooby.doobob@kakaoenterprise.com";
        String followeeEmail = "invalid-email@example.com";

        // when, then
        Assertions.assertThatThrownBy(() -> followService.followUser(followerEmail, followeeEmail))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }

    @Test
    void 유저_이메일_2개로_언팔로우_할_수_있다() {

        // given
        String followerEmail = "follower11@abc.com";
        String followeeEmail = "followee11@abc.com";

        // when
        FollowResponseDTO followResponseDTO = followService.unfollowUser(followerEmail, followeeEmail);

        // then
        Assertions.assertThat(followResponseDTO.getId()).isNotNull();
    }

    @Test
    void 존재하지_않는_유저_언팔로우_한다면_USER_NOT_FOUND_예외가_발생한다() {

        // given
        String followerEmail = "follower11@abc.com";
        String followeeEmail = "invalid@abc.com";

        // when
        // then
        Assertions.assertThatThrownBy(() -> followService.unfollowUser(followerEmail, followeeEmail))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }

    @Test
    void 존재하지_않는_팔로우_관계를_끊으려고_하면_예외_FOLLOW_RELATION_NOT_FOUND_예외가_발생한다() {
        // given
        String followerEmail = "ddooby.doobob1@kakaoenterprise.com";
        String followeeEmail = "ddooby.doobob@kakaoenterprise.com";

        //when then
        Assertions.assertThatThrownBy(() -> followService.unfollowUser(followerEmail, followeeEmail))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(FollowErrorCodeEnum.FOLLOW_RELATION_NOT_FOUND.getLongMessage());
    }

    @Test
    void 유저_이메일로_팔로워_목록을_얻을_수_있다() {

        // given
        String followeeEmail = "followee11@abc.com";

        //when
        FollowUserVOList followers = followService.getFollowers(followeeEmail);

        //then
        Assertions.assertThat(followers.getCount()).isEqualTo(2);
    }

    @Test
    void 팔로워_목록을_잘못된_유저로_요청시_USER_NOT_FOUND_예외가_발생한다() {

        //given
        String email = "adsadad@kakaoenterprise.com";

        //when //then
        Assertions.assertThatThrownBy(() -> followService.getFollowers(email))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }
}