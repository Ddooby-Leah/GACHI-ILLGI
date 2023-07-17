package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.domain.service.FollowService;
import com.ddooby.gachiillgi.domain.service.FollowUserVO;
import com.ddooby.gachiillgi.domain.service.FollowUserVOList;
import com.ddooby.gachiillgi.domain.service.UserService;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void followUser(@RequestBody FollowRequestDTO followRequestDTO) {
        followService.followUser(followRequestDTO.getFollowUserId(), followRequestDTO.getFollowedUserId());
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public void unfollowUser(@RequestBody FollowRequestDTO followRequestDTO) {
        followService.unfollowUser(followRequestDTO.getFollowUserId(), followRequestDTO.getFollowedUserId());
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public FollowUserVOList getMyFollowUser(@RequestParam Long userId) {
        return followService.getFollowers(userId);
    }
}
