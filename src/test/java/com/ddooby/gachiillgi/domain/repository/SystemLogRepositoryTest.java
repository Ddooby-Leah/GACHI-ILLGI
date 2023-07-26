package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.SystemLog;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SystemLogRepositoryTest {

    @Autowired
    SystemLogRepository systemLogRepository;

    @Test
    void 시스템_로그를_저장할_수_있다() {

        SystemLog systemLog = systemLogRepository.save(
                SystemLog.builder()
                        .level("info")
                        .message("뚜비두밥")
                        .createdBy("뚜비")
                        .build()
        );
        Assertions.assertThat(systemLog).isNotNull();

    }

}