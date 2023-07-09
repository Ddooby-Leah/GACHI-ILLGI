package com.ddooby.gachiillgi.base.enums.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthErrorCodeEnum implements ErrorCodeEnum {
    INVALID_CREDENTIALS("Invalid credentials", "Invalid username or password"),
    PASSWORD_MISMATCH("Password mismatch", "Passwords do not match"),
    INVALID_TOKEN("This token is unsupported", "Invalid token"),
    INVALID_TOKEN_SIGNATURE("잘못된 JWT 서명입니다.", "Invalid token"),
    EXPIRED_TOKEN("Token has expired", "Invalid token"),
    MISSING_AUTH_HEADER("Missing authorization header", "Invalid token"),
    UNAUTHORIZED_ACCESS("Unauthorized access to the requested resource", "Unauthorized access"),
    ACCESS_DENIED("Access to the requested resource is denied", "Access denied"),
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
