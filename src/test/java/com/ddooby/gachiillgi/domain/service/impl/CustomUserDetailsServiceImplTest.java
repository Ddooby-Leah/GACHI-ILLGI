package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum;
import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SpringBootTest
@SqlGroup(
        {
                @Sql(value = "/sql/custom-user-details-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        }
)
class CustomUserDetailsServiceImplTest {

    @Autowired
    CustomUserDetailsServiceImpl customUserDetailsService;

    @Test
    void 이메일로_Spring_Security의_UserDetails_객체를_만들_수_있다() {
        //given
        String email = "ddooby1@kakaoenterprise.com";

        //when
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        //then
        Assertions.assertThat(userDetails).isNotNull();
        Assertions.assertThat(userDetails.getUsername()).isEqualTo(email);
    }

    @Test
    void 등록되지_않은_유저로_loadUserByUsername_메소드_호출시_USER_NOT_FOUND_예외가_발생한다() {
        //given
        String invalidEmail = "ininiininivalid@asdada.com";

        //when then
        Assertions.assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(invalidEmail))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }

    @Test
    void ACTIVATED_상태가_아닌_유저는_loadUserByUsername_메소드_호출_시_MUST_MAIL_VERIFICATION_예외가_발생한다() {
        //given
        String email = "ddooby@kakaoenterprise.com";

        //when then
        Assertions.assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(email))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(AuthErrorCodeEnum.MUST_MAIL_VERIFICATION.getLongMessage());
    }

}