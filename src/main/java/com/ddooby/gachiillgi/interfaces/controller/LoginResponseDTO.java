package com.ddooby.gachiillgi.interfaces.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponseDTO {
    private final String token;
    private final Long tokenExpireTime;
}
