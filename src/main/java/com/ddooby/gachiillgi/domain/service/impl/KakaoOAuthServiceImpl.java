package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.service.OAuthService;
import com.ddooby.gachiillgi.interfaces.dto.response.KakaoOAuthResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.KakaoProfileResponseDTO;
import com.ddooby.gachiillgi.interfaces.feign.KakaoOAuthFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthServiceImpl implements OAuthService {

    private final KakaoOAuthFeignClient kakaoOAuthFeignClient;

    @Override
    public String getAccessToken(String authCode) {
        KakaoOAuthResponseDTO responseDTO =
                kakaoOAuthFeignClient.getAccessToken(URI.create("https://kauth.kakao.com"), authCode);

        if (Objects.isNull(responseDTO)) {
            throw new BizException(AuthErrorCodeEnum.KAKAO_AUTH_GET_TOKEN_ERROR);
        } else {
            log.debug("## KakaoOAuthResponseDTO : {}", responseDTO.toString());
            return responseDTO.getAccessToken();
        }
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public KakaoProfileResponseDTO getUserInfo(String accessToken) {
        KakaoProfileResponseDTO responseDTO =
                kakaoOAuthFeignClient.getUserInfo(URI.create("https://kapi.kakao.com"), "Bearer " + accessToken);

        if (Objects.isNull(responseDTO)) {
            throw new BizException(AuthErrorCodeEnum.KAKAO_AUTH_GET_USERINFO_ERROR);
        } else {
            log.debug("## KakaoProfileResponseDTO : {}", responseDTO.toString());
            return responseDTO;
        }
    }
}
