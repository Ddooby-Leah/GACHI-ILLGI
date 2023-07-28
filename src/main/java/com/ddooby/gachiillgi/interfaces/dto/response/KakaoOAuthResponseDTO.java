package com.ddooby.gachiillgi.interfaces.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class KakaoOAuthResponseDTO {
    @JsonProperty("token_type")
    private final String tokenType;

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("refresh_token")
    private final String refreshToken;

    @JsonProperty("expires_in")
    private final int expiresIn;

    @JsonProperty("refresh_token_expires_in")
    private final int refreshTokenExpiresIn;

    private final String scope;
}
