package com.ddooby.gachiillgi.interfaces.feign;

import com.ddooby.gachiillgi.interfaces.dto.response.KakaoOAuthResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.KakaoProfileResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Objects;

@Getter
@Slf4j
@NoArgsConstructor
public class FakeKakaoOAuthFeignClient implements KakaoOAuthFeignClient {

    private String authCode;
    private String accessToken;
    private URI requestUri;

    @Override
    public KakaoOAuthResponseDTO getAccessToken(URI requestUri, String code) {

        if (code == null) {
            return null;
        }

        this.authCode = code;
        this.requestUri = requestUri;

        return KakaoOAuthResponseDTO.builder()
                .accessToken("accesstoken")
                .expiresIn(100)
                .refreshToken("refreshtoken")
                .tokenType("ddoobytoken")
                .scope("scope")
                .refreshTokenExpiresIn(100)
                .build();
    }

    @Override
    public KakaoProfileResponseDTO getUserInfo(URI requestUri, String accessToken) {
        if (Objects.equals(accessToken, "Bearer ")) {
            return null;
        }

        this.accessToken = accessToken.substring(7);
        this.requestUri = requestUri;

        return KakaoProfileResponseDTO.builder()
                .id("ddooby")
                .build();
    }

    @Override
    public String logout(URI requestUri, String loginId) {
        return null;
    }
}