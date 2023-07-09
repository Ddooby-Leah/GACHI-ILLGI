package com.ddooby.gachiillgi.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserStatusEnum {
    PENDING("PENDING"), // 인증 대기
    ACTIVATED("ACTIVATED"); // 인증 완료

    private String status;

}
