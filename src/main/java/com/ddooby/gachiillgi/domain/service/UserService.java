package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.interfaces.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    UserDTO signup(UserDTO userDto);

    @Transactional(readOnly = true)
    UserDTO getUserWithAuthorities(String username);

    @Transactional(readOnly = true)
    UserDTO getMyUserWithAuthorities();
}
