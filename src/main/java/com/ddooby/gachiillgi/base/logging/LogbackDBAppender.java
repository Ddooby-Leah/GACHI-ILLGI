package com.ddooby.gachiillgi.base.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.ddooby.gachiillgi.base.util.SecurityUtil;
import com.ddooby.gachiillgi.domain.service.SystemLogService;
import com.ddooby.gachiillgi.domain.vo.SystemLogCommandVO;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LogbackDBAppender extends UnsynchronizedAppenderBase<ILoggingEvent> implements ApplicationContextAware {

    private static SystemLogService systemLogService;

    @Override
    protected void append(ILoggingEvent eventObject) {
        systemLogService.save(
                SystemLogCommandVO.builder()
                        .level(eventObject.getLevel().toString())
                        .message(eventObject.getFormattedMessage())
                        .createdBy(SecurityUtil.getCurrentUserEmail().isPresent()
                                ? SecurityUtil.getCurrentUserEmail().get() : "")
                        .build());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        systemLogService = applicationContext.getAutowireCapableBeanFactory().getBean(SystemLogService.class);
    }
}
