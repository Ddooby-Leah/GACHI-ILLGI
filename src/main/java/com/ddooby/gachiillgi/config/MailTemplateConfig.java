package com.ddooby.gachiillgi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class MailTemplateConfig {
    @Bean
    public TemplateEngine htmlTemplateEngine(SpringResourceTemplateResolver springResourceTemplateResolver) {
        TemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(springResourceTemplateResolver);

        return templateEngine;
    }
}

