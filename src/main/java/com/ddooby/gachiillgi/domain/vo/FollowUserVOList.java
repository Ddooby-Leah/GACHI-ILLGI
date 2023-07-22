package com.ddooby.gachiillgi.domain.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FollowUserVOList {

    private final List<FollowUserVO> list;

    public int getCount() {
        return this.list.size();
    }
}
