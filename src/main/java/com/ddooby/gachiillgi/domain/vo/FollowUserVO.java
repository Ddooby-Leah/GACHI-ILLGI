package com.ddooby.gachiillgi.domain.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class FollowUserVO {

    private final String email;
    private final String nickname;
    private final String sex;
}
