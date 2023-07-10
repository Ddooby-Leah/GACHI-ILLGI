package com.ddooby.gachiillgi.base.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.ddooby.gachiillgi.base.util.SecurityUtil;
import com.ddooby.gachiillgi.domain.service.SystemLogCommand;
import com.ddooby.gachiillgi.domain.service.SystemLogService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class LogbackDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> implements ApplicationContextAware {

    private static SystemLogService systemLogService;

    @Override
    protected void append(ILoggingEvent eventObject) {
        systemLogService.save(
                SystemLogCommand.builder()
                        .level(eventObject.getLevel().toString())
                        .message(eventObject.getFormattedMessage())
                        .createdBy(SecurityUtil.getCurrentUsername().isPresent()
                                ? SecurityUtil.getCurrentUsername().get() : "")
                        .build());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        systemLogService = applicationContext.getAutowireCapableBeanFactory().getBean(SystemLogService.class);
    }
}
