package com.ddooby.gachiillgi.base.util;

import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CookieUtil {

    private static final String SET_COOKIE = "Set-Cookie";

    private CookieUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, long maxAge) {

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .maxAge(maxAge)
                .path("/")
                .build();

        response.addHeader(SET_COOKIE, cookie.toString());
    }

    public static void addSecureCookie(HttpServletResponse response, String name, String value, long maxAge) {

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .maxAge(maxAge)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .build();

        response.addHeader(SET_COOKIE, cookie.toString());
    }

    public static void addPublicCookie(HttpServletResponse response, String name, String value) {

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .build();


        response.addHeader(SET_COOKIE, cookie.toString());
    }

    public static void resetCookie(HttpServletResponse response, String name, String value) {

        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(SET_COOKIE, cookie.toString());
    }
//    public static void resetDefaultCookies(HttpServletResponse response) {
//        Map<String,String> cookies = new HashMap<>();
//        cookies.put("atk", "");
//        cookies.put("rtk", "");
//        cookies.put("isLogin", "false");
//        cookies.put("todayDiaryId", "");
//        cookies.put("userId", "");
//        cookies.put("isSocial", "");
//        for(String i : cookies.keySet()){ // NOSONAR
//            ResponseCookie cookie = ResponseCookie.from(i, cookies.get(i))
//                    .path("/")
//                    .maxAge(0)
//                    .build();
//            response.addHeader(SET_COOKIE, cookie.toString());
//        }
//    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

//    public static String getAccessToken(HttpServletRequest request) {
//        Cookie accessTokenCookie = Arrays.stream(request.getCookies())
//                .filter(req -> req.getName().equals("atk"))
//                .findAny()
//                .orElseThrow(() -> new BizException(JwtExceptionType.EMPTY_TOKEN));
//
//        return accessTokenCookie.getValue();
//    }

//    public static String getRefreshToken(HttpServletRequest request) {
//
//        Cookie refreshTokenCookie = Arrays.stream(request.getCookies())
//                .filter(req -> req.getName().equals("rtk"))
//                .findAny()
//                .orElseThrow(() -> new BizException(JwtExceptionType.EMPTY_TOKEN));
//
//        return refreshTokenCookie.getValue();
//    }

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }

}

