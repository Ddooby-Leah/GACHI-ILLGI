package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.interfaces.dto.request.UserRequestDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    UserRequestDTO signup(UserRequestDTO userRequestDto);

    @Transactional
    void updateActivated(String username);

    @Transactional(readOnly = true)
    UserRequestDTO getUserWithAuthorities(String username);

    @Transactional(readOnly = true)
    UserRequestDTO getMyUserWithAuthorities();
}
