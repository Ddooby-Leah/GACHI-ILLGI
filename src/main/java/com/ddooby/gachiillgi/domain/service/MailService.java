package com.ddooby.gachiillgi.domain.service;

import java.util.Map;

public interface MailService {

    void send(String subject, Map<String, Object> variables, String... to);
}
