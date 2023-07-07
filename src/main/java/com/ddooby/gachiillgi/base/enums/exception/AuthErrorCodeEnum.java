package com.ddooby.gachiillgi.base.enums.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCodeEnum implements ErrorCodeEnum {
    INVALID_CREDENTIALS("Invalid credentials", "Invalid username or password"),
    PASSWORD_MISMATCH("Password mismatch", "Passwords do not match"),
    INVALID_TOKEN("Invalid token", "Invalid token"),
    EXPIRED_TOKEN("Expired token", "Token has expired"),
    MISSING_AUTH_HEADER("Missing authorization header", "Authorization header is missing"),
    UNAUTHORIZED_ACCESS("Unauthorized access", "Unauthorized access to the requested resource"),
    ACCESS_DENIED("Access denied", "Access to the requested resource is denied"),
    ;

    private final String longMessage;
    private final String shortMessage;

    public static AuthErrorCodeEnum findByType (String type) {
        try {
            return AuthErrorCodeEnum.valueOf(type);
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
