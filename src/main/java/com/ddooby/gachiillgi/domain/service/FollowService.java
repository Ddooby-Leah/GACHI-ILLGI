package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.domain.vo.FollowUserVOList;
import com.ddooby.gachiillgi.interfaces.dto.response.FollowResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface FollowService {

    @Transactional
    FollowResponseDTO followUser(String followerEmail, String follweeEmail);

    @Transactional
    FollowResponseDTO unfollowUser(String followerEmail, String follweeEmail);

    @Transactional
    FollowUserVOList getFollowers(String userEmail);
}
