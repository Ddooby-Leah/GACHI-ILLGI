package com.ddooby.gachiillgi.base.jwt;

import com.ddooby.gachiillgi.base.enums.PermitPathEnum;
import com.ddooby.gachiillgi.base.enums.exception.ErrorCodeEnum;
import com.ddooby.gachiillgi.base.handler.BizException;
import com.ddooby.gachiillgi.interfaces.dto.DefaultErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.ddooby.gachiillgi.base.enums.TokenEnum.TOKEN_COOKIE_HEADER;
import static com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum.UNAUTHORIZED_ACCESS;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final RequestMatcher[] permitMatchers;
    private final AntPathMatcher antPathMatcher;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.permitMatchers = PermitPathEnum.getPermitUriList();
        this.antPathMatcher = new AntPathMatcher();
    }

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse,
                                 FilterChain filterChain) throws IOException, ServletException, BizException {

        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (isPermitUri(httpServletRequest)) {
            log.debug("허용된 URI 입니다. uri: {}", requestURI);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            try {
                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                    Authentication authentication = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                }
                else {
                    buildErrorDTO(httpServletResponse, UNAUTHORIZED_ACCESS, HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (BizException e) {
                log.debug("Invalid Access Token, uri: {}", requestURI);
                buildErrorDTO(httpServletResponse, e.getCode(), HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> TOKEN_COOKIE_HEADER.getName().equals(cookie.getName()))
                    .findFirst();
            if (tokenCookie.isPresent()) {
                return tokenCookie.get().getValue();
            }
        }
        return null;
    }

    private boolean isPermitUri(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (RequestMatcher matcher : permitMatchers) {
            if (matcher instanceof AntPathRequestMatcher) {
                AntPathRequestMatcher antMatcher = (AntPathRequestMatcher) matcher;
                if (antPathMatcher.match(antMatcher.getPattern(), requestURI)) {
                    return true;
                }
            } else if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    private void buildErrorDTO(HttpServletResponse httpServletResponse, ErrorCodeEnum code, int httpStatus) {

        httpServletResponse.setStatus(httpStatus);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        DefaultErrorResponseDTO errorResponseDTO = DefaultErrorResponseDTO.error(
                code.getName(),
                code.getLongMessage(),
                code.getShortMessage());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorResponseDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
