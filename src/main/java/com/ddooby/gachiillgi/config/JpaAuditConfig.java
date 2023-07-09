package com.ddooby.gachiillgi.config;

import com.ddooby.gachiillgi.base.util.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaAuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        // 람다를 이용
        return SecurityUtil::getCurrentUsername;
    }
}
