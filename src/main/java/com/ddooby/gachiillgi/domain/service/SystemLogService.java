package com.ddooby.gachiillgi.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface SystemLogService {
    @Transactional
    void save(SystemLogCommand systemLogCommand);
}
