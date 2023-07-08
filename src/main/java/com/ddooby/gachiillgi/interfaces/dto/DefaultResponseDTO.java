package com.ddooby.gachiillgi.interfaces.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class DefaultResponseDTO {

    private final String code = "1";
    private final String longMessage = "SUCCESS";
    private final String shortMessage = "SUCCESS";
    private final Object contents;
}
