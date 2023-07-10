package com.ddooby.gachiillgi.domain.repository;

import com.ddooby.gachiillgi.domain.entity.SystemLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, String> {
}
