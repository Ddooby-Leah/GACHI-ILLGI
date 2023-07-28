package com.ddooby.gachiillgi.base.enums.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SystemErrorCodeEnum implements ErrorCodeEnum {
    SAVE_LOG_FAILED("시스템 로그 저장 시 에러 발생", "시스템 로그 에러"),
    ;

    private final String longMessage;
    private final String shortMessage;

    public static SystemErrorCodeEnum findByType(String type) {
        try {
            return SystemErrorCodeEnum.valueOf(type);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    @Override
    public String getLongMessage() {
        return longMessage;
    }

    @Override
    public String getShortMessage() {
        return shortMessage;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
