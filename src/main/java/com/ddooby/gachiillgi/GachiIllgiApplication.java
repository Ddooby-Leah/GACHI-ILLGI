package com.ddooby.gachiillgi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GachiIllgiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GachiIllgiApplication.class, args);
    }
}


