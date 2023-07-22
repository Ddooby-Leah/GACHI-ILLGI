package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.interfaces.dto.response.KakaoProfileResponseDTO;

public interface OAuthService {

    String getAccessToken(String authCode);

    boolean login();

    boolean logout();

    KakaoProfileResponseDTO getUserInfo(String accessToken);

}
