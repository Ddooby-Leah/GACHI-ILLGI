package com.ddooby.gachiillgi.base.util;

import com.ddooby.gachiillgi.base.enums.exception.CommonErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.interfaces.dto.response.DefaultResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

import static com.ddooby.gachiillgi.base.enums.TokenEnum.TOKEN_COOKIE_HEADER;

@Slf4j
public class CommonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static String ObjectToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BizException(CommonErrorCodeEnum.JSON_MARSHALLING_ERROR);
        }
    }

    public static DefaultResponseDTO objectToDefaultResponseDTO(Object object) {
        try {
            return DefaultResponseDTO.create(object);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BizException(CommonErrorCodeEnum.JSON_MARSHALLING_ERROR);
        }
    }

    public static URI convertStringToUri(String string) {
        log.debug(objectMapper.toString());
        try {
            return new URI(string);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpHeaders buildAuthCookie(String token, Long maxAge) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(
                HttpHeaders.SET_COOKIE,
                ResponseCookie.from(TOKEN_COOKIE_HEADER.getName(), token)
                        .maxAge(maxAge)
                        .secure(true)
//                        .httpOnly(true)
                        .path("/")
                        .build()
                        .toString()
        );
        return httpHeaders;
    }

    public static void addAuthCookie(String token, int maxAge, HttpServletResponse httpServletResponse) {
        Cookie authCookie = new Cookie(TOKEN_COOKIE_HEADER.getName(), token);
        authCookie.setMaxAge(maxAge);
        authCookie.setSecure(true);
        authCookie.setPath("/");
        authCookie.setHttpOnly(false);
        httpServletResponse.addCookie(authCookie);
    }
}
