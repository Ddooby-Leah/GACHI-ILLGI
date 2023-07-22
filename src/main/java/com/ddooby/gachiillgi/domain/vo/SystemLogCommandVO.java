package com.ddooby.gachiillgi.domain.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SystemLogCommandVO {
    private String level;
    private String message;
    private String createdBy;
}
