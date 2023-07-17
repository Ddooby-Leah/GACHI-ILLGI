package com.ddooby.gachiillgi.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FollowUserVO {

    private final Long userId;
    private final String name;
    private final String nickname;
    private final String sex;
}
