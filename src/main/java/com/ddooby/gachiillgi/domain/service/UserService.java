package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.interfaces.dto.request.UserDetailInfoRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.UserRegisterResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.UserUpdateResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    UserRegisterResponseDTO signup(UserRegisterRequestDTO requestDTO);

    @Transactional
    UserRegisterResponseDTO signupWithDetail(UserDetailInfoRegisterRequestDTO requestDTO);

    @Transactional
    UserUpdateResponseDTO updateActivated(String email);

    @Transactional(readOnly = true)
    UserRegisterResponseDTO getUserWithAuthorities(String email);

    @Transactional(readOnly = true)
    UserRegisterResponseDTO getMyUserWithAuthorities();

    @Transactional(readOnly = true)
    boolean isUser(String email);
}
