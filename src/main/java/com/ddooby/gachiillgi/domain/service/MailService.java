package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.interfaces.dto.response.MailServiceResponseDTO;

import java.util.Map;

public interface MailService {
    MailServiceResponseDTO send(String subject, Map<String, Object> variables, String to);
}
