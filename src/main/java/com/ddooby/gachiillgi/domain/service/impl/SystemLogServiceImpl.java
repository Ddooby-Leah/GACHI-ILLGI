package com.ddooby.gachiillgi.domain.service.impl;

import com.ddooby.gachiillgi.domain.entity.SystemLog;
import com.ddooby.gachiillgi.domain.repository.SystemLogRepository;
import com.ddooby.gachiillgi.domain.service.SystemLogCommand;
import com.ddooby.gachiillgi.domain.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogServiceImpl implements SystemLogService {

    private final SystemLogRepository systemLogRepository;

    @Override
    public void save(SystemLogCommand systemLogCommand) {
        systemLogRepository.save(
                SystemLog.builder()
                        .level(systemLogCommand.getLevel())
                        .message(systemLogCommand.getMessage())
                        .createdBy(systemLogCommand.getCreatedBy())
                        .build()
        );
    }
}
