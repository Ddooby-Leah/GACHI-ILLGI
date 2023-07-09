package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.base.jwt.TokenProvider;
import com.ddooby.gachiillgi.domain.service.MailService;
import com.ddooby.gachiillgi.domain.service.UserService;
import com.ddooby.gachiillgi.interfaces.dto.LoginRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.MailSendDTO;
import com.ddooby.gachiillgi.interfaces.dto.TokenDTO;
import com.ddooby.gachiillgi.interfaces.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.ddooby.gachiillgi.base.enums.TokenEnum.TOKEN_COOKIE_HEADER;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    @Value("${jwt.token-validity-in-seconds}")
    private String tokenExpireTime;

    private final UserService userService;
    private final MailService mailService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/signup")
    public UserDTO signup(@Valid @RequestBody UserDTO userDto) {
        return userService.signup(userDto);
    }

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendVerificationMail(@RequestBody MailSendDTO mailSendDTO) { // FIXME 코드 너무 더럽;

        String email = mailSendDTO.getEmail();
        String username = mailSendDTO.getUsername();
        String temporaryLink = tokenProvider.createTemporaryLink(mailSendDTO.getUsername());

        //TODO 유저 검증이 필요할까?

        log.debug("email = {} username = {} tempLink = {}", email, username, temporaryLink);
        String subject = "[가치일기] 안녕하세요, " + mailSendDTO.getUsername() + "님! 메일인증을 완료해주세요.";
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", mailSendDTO.getUsername() + "님 환영합니다!");
        variables.put("link", "http://localhost:8080/api/auth/verify-mail/?link=" + temporaryLink);
        variables.put("location", "https://가치일기.com");
        mailService.send(subject, variables, mailSendDTO.getEmail());

        return ResponseEntity.ok("인증 메일 전송 완료");
    }

    @GetMapping("/verify-mail")
    public RedirectView verifyVerificationMail(@RequestParam String link) { // FIXME 어떻게 정리할까..?
        String usernameByLink = tokenProvider.verifyTemporaryLink(link);
        if (usernameByLink != null) {
            userService.updateActivated(usernameByLink);
            return new RedirectView("https://google.com");
        } else {
            return new RedirectView("/error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginRequestDTO loginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(
                HttpHeaders.SET_COOKIE,
                ResponseCookie.from(TOKEN_COOKIE_HEADER.getName(), token)
                        .maxAge(Long.parseLong(tokenExpireTime))
                        .secure(true)
                        .httpOnly(true)
                        .path("/")
                        .build()
                        .toString()
        );

        return new ResponseEntity<>(new TokenDTO(token), httpHeaders, HttpStatus.OK);
    }
}
