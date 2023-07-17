package com.ddooby.gachiillgi.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface FollowService {

    void followUser(Long followingUserId, Long followUserId);

    void unfollowUser(Long followingUserId, Long followUserId);

    FollowUserVOList getFollowers(Long userId);
}
