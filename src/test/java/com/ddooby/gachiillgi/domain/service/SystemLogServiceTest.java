package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.base.enums.exception.SystemErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.domain.repository.SystemLogRepository;
import com.ddooby.gachiillgi.domain.service.impl.SystemLogServiceImpl;
import com.ddooby.gachiillgi.domain.vo.SystemLogCommandVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class SystemLogServiceTest {

    @InjectMocks
    private SystemLogServiceImpl systemLogService;

    @Mock
    private SystemLogRepository systemLogRepository;

    @Test
    void 시스템로그를_저장_할_수_있다() {
        //given
        SystemLogCommandVO systemLogCommandVO = SystemLogCommandVO.builder()
                .level("info")
                .message("asdadsad")
                .createdBy("ddobou")
                .build();

        // 예외를 던지도록 설정
        doThrow(new RuntimeException("예외 발생")).when(systemLogRepository).save(any());

        //when & then
        Assertions.assertThatThrownBy(() -> systemLogService.save(systemLogCommandVO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("예외 발생");
    }
    
    @Test
    void null인_시스템로그를_DB에_저장하는_경우_예외가_발생한다() {
        //given
        //when & then
        Assertions.assertThatThrownBy(() -> systemLogService.save(null))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(SystemErrorCodeEnum.SAVE_LOG_FAILED.getLongMessage());

    }
}
