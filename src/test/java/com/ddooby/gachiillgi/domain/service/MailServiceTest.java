package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.repository.UserRepository;
import com.ddooby.gachiillgi.domain.service.impl.AmazonSESMailServiceImpl;
import com.ddooby.gachiillgi.domain.service.impl.FakeMailService;
import com.ddooby.gachiillgi.interfaces.dto.response.MailServiceResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/mail-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class MailServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private FakeMailService fakeMailService;

    @Test
    void 제목과_내용_수신자메일로_이메일을_보낼_수_있는지_테스트() {
        //given
        MailService mailService = new AmazonSESMailServiceImpl(userRepository, templateEngine, fakeMailService);
        String nickname = "ddooby";
        String email = "ddooby@kakaoenterprise.com";
        String subject = "[가치일기] 안녕하세요, " + nickname + "님! 메일인증을 완료해주세요.";
        Map<String, Object> variables = new HashMap<>();
        variables.put("nickname", nickname + "님 환영합니다!");

        //when
        MailServiceResponseDTO result = mailService.send(subject, variables, email);

        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(fakeMailService.getSubject()).isEqualTo(subject);
    }

    @Test
    void 존재하지_않는_유저로_이메일_전송_시_USER_NOT_FOUND_예외_처리() {
        //given
        MailService mailService = new AmazonSESMailServiceImpl(userRepository, templateEngine, fakeMailService);
        String invalidEmail = "ddoasdsadoby@kakaoenterprise.com";
        String subject = "[가치일기]";
        Map<String, Object> variables = new HashMap<>();
        variables.put("nickname", "님 환영합니다!");

        //when then
        Assertions.assertThatThrownBy(() -> mailService.send(subject, variables, invalidEmail))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }

    @Test
    void 이미_인증된_사용자가_인증_시도하면_USER_NOT_PENDING_STATUS_예외_처리() {
        //given
        String email = "ddooby1@kakaoenterprise.com";
        MailService mailService = new AmazonSESMailServiceImpl(userRepository, templateEngine, fakeMailService);
        String subject = "[가치일기]";
        Map<String, Object> variables = new HashMap<>();
        variables.put("nickname", "님 환영합니다!");

        //when then
        Assertions.assertThatThrownBy(() -> mailService.send(subject, variables, email))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_PENDING_STATUS.getLongMessage());
    }
}