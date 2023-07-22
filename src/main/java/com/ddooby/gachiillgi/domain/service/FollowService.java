package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.domain.vo.FollowUserVOList;
import org.springframework.transaction.annotation.Transactional;

public interface FollowService {

    @Transactional
    void followUser(Long followerId, Long followeeId);

    @Transactional
    void unfollowUser(Long followerId, Long followeeId);

    @Transactional
    FollowUserVOList getFollowers(Long userId);
}
