package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.service.impl.KakaoOAuthServiceImpl;
import com.ddooby.gachiillgi.interfaces.dto.response.KakaoProfileResponseDTO;
import com.ddooby.gachiillgi.interfaces.feign.FakeKakaoOAuthFeignClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

class OAuthServiceTest {

    FakeKakaoOAuthFeignClient fakeKakaoOAuthFeignClient = new FakeKakaoOAuthFeignClient();
    KakaoOAuthServiceImpl kakaoOAuthService = new KakaoOAuthServiceImpl(fakeKakaoOAuthFeignClient);

    @Test
    void 인가코드로_카카오_OAUTH_엑세스_토큰을_가져올_수_있다() {
        //given
        String authCode = "authauth";

        //when
        String accessToken = kakaoOAuthService.getAccessToken(authCode);

        //then
        Assertions.assertThat(accessToken).isEqualTo("accesstoken");
        Assertions.assertThat(fakeKakaoOAuthFeignClient.getAuthCode()).isEqualTo(authCode);
        Assertions.assertThat(fakeKakaoOAuthFeignClient.getRequestUri()).isEqualTo(URI.create("https://kauth.kakao.com"));
    }

    @Test
    void 인가코드가_NULL이면_KAKAO_AUTH_GET_TOKEN_ERROR_예외가_발생한다() {
        //given when then
        Assertions.assertThatThrownBy(() -> kakaoOAuthService.getAccessToken(null))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(AuthErrorCodeEnum.KAKAO_AUTH_GET_TOKEN_ERROR.getLongMessage());
    }

    @Test
    void accessToken으로_유저정보를_받아올_수_있다() {
        //given
        String accessToken = "accessToken";

        //when
        KakaoProfileResponseDTO userInfo = kakaoOAuthService.getUserInfo(accessToken);

        //then
        Assertions.assertThat(userInfo.getId()).isEqualTo("ddooby");
        Assertions.assertThat(fakeKakaoOAuthFeignClient.getAccessToken()).isEqualTo(accessToken);
    }

    @Test
    void accessToken이_비어있이면_getUserInfo_호출_시_예외가_발생한다() {
        //given when then
        Assertions.assertThatThrownBy(() -> kakaoOAuthService.getUserInfo(""))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(AuthErrorCodeEnum.KAKAO_AUTH_GET_USERINFO_ERROR.getLongMessage());
    }
}