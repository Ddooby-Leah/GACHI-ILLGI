package com.ddooby.gachiillgi.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MailServiceResponseDTO {
    private final String messageId;
}
