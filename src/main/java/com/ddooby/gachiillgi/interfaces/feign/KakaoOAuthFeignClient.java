package com.ddooby.gachiillgi.interfaces.feign;

import com.ddooby.gachiillgi.interfaces.dto.response.KakaoOAuthResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.KakaoProfileResponseDTO;
import com.ddooby.gachiillgi.interfaces.feign.config.KakaoOAuthFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "KakaoOAuthClient", url = "https://kauth.api.com", configuration = KakaoOAuthFeignClientConfig.class)
public interface KakaoOAuthFeignClient {
    @PostMapping(value = "/oauth/token")
    KakaoOAuthResponseDTO getAccessToken(URI requestUri, @RequestParam("code") String code);

    @PostMapping(value = "/v2/user/me")
    KakaoProfileResponseDTO getUserInfo(URI requestUri, @RequestHeader("Authorization") String accessToken);

    @PostMapping(value = "kapi.kakao.com")
        // todo 추후 구현
    String logout(URI requestUri, @RequestParam("target_id") String loginId);
}
