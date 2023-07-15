package com.ddooby.gachiillgi.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@ToString
@RequiredArgsConstructor
public class DefaultResponseDTO {

    private final String code = "1";
    private final String longMessage = "SUCCESS";
    private final String shortMessage = "SUCCESS";
    private final Object contents;
}
