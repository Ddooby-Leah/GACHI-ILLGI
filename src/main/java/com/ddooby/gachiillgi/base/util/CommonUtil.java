package com.ddooby.gachiillgi.base.util;

import com.ddooby.gachiillgi.base.enums.exception.CommonErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CommonUtil {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static String ObjectToJsonString(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BizException(CommonErrorCodeEnum.JSON_MARSHALLING_ERROR);
        }
    }
}
