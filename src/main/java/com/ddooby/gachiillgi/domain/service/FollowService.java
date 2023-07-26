package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.domain.vo.FollowUserVOList;
import org.springframework.transaction.annotation.Transactional;

public interface FollowService {

    @Transactional
    void followUser(String followerEmail, String follweeEmail);

    @Transactional
    void unfollowUser(String followerEmail, String follweeEmail);

    @Transactional
    FollowUserVOList getFollowers(Long userId);
}
