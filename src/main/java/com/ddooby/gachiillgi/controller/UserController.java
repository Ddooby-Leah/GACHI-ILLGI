package com.ddooby.gachiillgi.controller;

import com.ddooby.gachiillgi.dto.UserDTO;
import com.ddooby.gachiillgi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    @PostMapping("/signup")
    public UserDTO signup(@Valid @RequestBody UserDTO userDto) {
        return userService.signup(userDto);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserDTO getMyUserInfo(HttpServletRequest request) {
        log.debug("okokok");
        return userService.getMyUserWithAuthorities();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserDTO getUserInfo(@PathVariable String username) {
        return userService.getUserWithAuthorities(username);
    }
}
