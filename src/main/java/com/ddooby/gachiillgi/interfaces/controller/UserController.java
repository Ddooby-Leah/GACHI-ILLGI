package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.domain.service.UserService;
import com.ddooby.gachiillgi.interfaces.dto.response.UserRegisterResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserRegisterResponseDTO getMyUserInfo() {
        return userService.getMyUserWithAuthorities();
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserRegisterResponseDTO getUserInfo(@PathVariable String email) {
        return userService.getUserWithAuthorities(email);
    }
}
