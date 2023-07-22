package com.ddooby.gachiillgi.base.util;

import com.ddooby.gachiillgi.base.enums.exception.CommonErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

import static com.ddooby.gachiillgi.base.enums.TokenEnum.TOKEN_COOKIE_HEADER;


public class CommonUtil {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jwt.token-validity-in-seconds}")
    private static String TOKEN_EXPIRE_TIME;

    public static String ObjectToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new BizException(CommonErrorCodeEnum.JSON_MARSHALLING_ERROR);
        }
    }

    public static URI convertStringToUri(String string) {
        try {
            return new URI(string);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpHeaders buildAuthCookie(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(
                HttpHeaders.SET_COOKIE,
                ResponseCookie.from(TOKEN_COOKIE_HEADER.getName(), token)
                        .maxAge(Long.parseLong(TOKEN_EXPIRE_TIME))
                        .secure(true)
//                        .httpOnly(true)
                        .path("/")
                        .build()
                        .toString()
        );
        return httpHeaders;
    }

    public static void addAuthCookie(String token, HttpServletResponse httpServletResponse) {
        Cookie authCookie = new Cookie(TOKEN_COOKIE_HEADER.getName(), token);
        authCookie.setMaxAge(Integer.parseInt(TOKEN_EXPIRE_TIME));
        authCookie.setSecure(true);
        authCookie.setPath("/");
        authCookie.setHttpOnly(false);
        httpServletResponse.addCookie(authCookie);
    }
}
