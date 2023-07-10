package com.ddooby.gachiillgi.base.handler;

import com.ddooby.gachiillgi.base.enums.exception.CommonErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.base.util.CommonUtil;
import com.ddooby.gachiillgi.interfaces.dto.DefaultErrorResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.DefaultResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class DefaultResponseHandler implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (selectedContentType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
            if (body instanceof DefaultErrorResponseDTO) {
                return body;
            } else {
                return DefaultResponseDTO.builder().contents(body).build();
            }
        } else if (selectedContentType.isCompatibleWith(MediaType.TEXT_PLAIN)) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return CommonUtil.ObjectToJsonString(DefaultResponseDTO.builder().contents(body).build());
        } else {
            return body;
        }
    }
}
