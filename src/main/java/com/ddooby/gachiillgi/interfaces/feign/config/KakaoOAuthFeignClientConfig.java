package com.ddooby.gachiillgi.interfaces.feign.config;

import com.ddooby.gachiillgi.base.util.CommonUtil;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.net.URI;

@Slf4j
public class KakaoOAuthFeignClientConfig {

    @Value("${kakao.auth.token.grant-type}")
    private String grantType;
    @Value("${kakao.auth.token.rest-api-key}")
    private String clientId;
    @Value("${kakao.auth.token.redirect-url}")
    private String redirectUrl;
    @Value("${kakao.auth.admin-key}")
    private String adminKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {

            URI encodedRedirectUrl = CommonUtil.convertStringToUri(redirectUrl);

            template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            // login
            if (template.url().startsWith("https://kauth.kakao.com/oauth/token")) {
                template.query("grant_type", grantType);
                template.query("client_id", clientId);
                template.query("redirect_uri", String.valueOf(encodedRedirectUrl));
            }

            // logout
            if (template.url().startsWith("https://kapi.kakao.com/?target")) {
                template.header("Authorization", "KakaoAK " + adminKey);
                template.query("target_id_type", "user_id");
            }

            log.debug("*** Kakao Request data : {}", template.request());
        };
    }
}
