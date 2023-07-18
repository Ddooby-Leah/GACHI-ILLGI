package com.ddooby.gachiillgi.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface FollowService {

    void followUser(Long followerId, Long followeeId);

    void unfollowUser(Long followerId, Long followeeId);

    FollowUserVOList getFollowers(Long userId);
}
