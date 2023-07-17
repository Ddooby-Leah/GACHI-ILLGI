package com.ddooby.gachiillgi.interfaces.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FollowRequestDTO {
    private final Long followUserId;
    private final Long followedUserId;
}
