package com.ddooby.gachiillgi.domain.service;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SystemLogCommand {
    private String level;
    private String message;
    private String createdBy;
}
