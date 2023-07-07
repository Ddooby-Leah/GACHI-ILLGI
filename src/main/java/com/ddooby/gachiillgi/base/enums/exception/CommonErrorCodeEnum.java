package com.ddooby.gachiillgi.base.enums.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommonErrorCodeEnum implements ErrorCodeEnum {
    REQUEST_ERROR("Invalid Request", "Invalid Request"),
    ;

    private final String longMessage;
    private final String shortMessage;

    public static CommonErrorCodeEnum findByType (String type) {
        try {
            return CommonErrorCodeEnum.valueOf(type);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    @Override
    public String getLongMessage() { return longMessage; }

    @Override
    public String getShortMessage() {
        return shortMessage;
    }

    @Override
    public String getName() {
        return this.name();
    }
}
