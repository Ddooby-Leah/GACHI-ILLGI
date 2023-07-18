package com.ddooby.gachiillgi.base.enums.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FollowErrorCodeEnum implements ErrorCodeEnum {

    FOLLOW_RELATION_NOT_FOUND("Follow relation is not found", "follow error");


    private final String longMessage;
    private final String shortMessage;


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
