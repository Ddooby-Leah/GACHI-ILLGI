package com.ddooby.gachiillgi.interfaces.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FollowRequestDTO {
    private final String followerEmail;
    private final String followeeEmail;
}
