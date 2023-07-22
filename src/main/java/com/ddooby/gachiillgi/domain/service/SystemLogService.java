package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.domain.vo.SystemLogCommandVO;
import org.springframework.transaction.annotation.Transactional;

public interface SystemLogService {
    @Transactional
    void save(SystemLogCommandVO systemLogCommandVO);
}
