package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.domain.service.FollowService;
import com.ddooby.gachiillgi.domain.vo.FollowUserVOList;
import com.ddooby.gachiillgi.interfaces.dto.request.FollowRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.FollowResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public FollowResponseDTO followUser(@RequestBody FollowRequestDTO followRequestDTO) {
        return followService.followUser(followRequestDTO.getFollowerEmail(), followRequestDTO.getFolloweeEmail());
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public FollowResponseDTO unfollowUser(@RequestBody FollowRequestDTO followRequestDTO) {
        return followService.unfollowUser(followRequestDTO.getFollowerEmail(), followRequestDTO.getFolloweeEmail());
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public FollowUserVOList getMyFollowUser(@RequestParam String userEmail) {
        return followService.getFollowers(userEmail);
    }
}
