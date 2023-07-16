package com.ddooby.gachiillgi.base.enums.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCodeEnum implements ErrorCodeEnum {

    USER_NOT_FOUND("User not found", "User not found"),
    DUPLICATE_EMAIL("Duplicate email", "Email already exists"),
    DUPLICATE_NICKNAME("Duplicate nickname", "Nickname already exists"),
    INVALID_PASSWORD("Invalid password", "Invalid password"),
    INVALID_EMAIL_FORMAT("Invalid email format", "Invalid email format"),;

    private final String longMessage;
    private final String shortMessage;

    public static UserErrorCodeEnum findByType (String type) {
        try {
            return UserErrorCodeEnum.valueOf(type);
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
