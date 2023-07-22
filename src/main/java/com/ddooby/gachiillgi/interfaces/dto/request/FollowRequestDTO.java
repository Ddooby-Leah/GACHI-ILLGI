package com.ddooby.gachiillgi.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FollowRequestDTO {
    private final Long followerId;
    private final Long followeeId;
}
