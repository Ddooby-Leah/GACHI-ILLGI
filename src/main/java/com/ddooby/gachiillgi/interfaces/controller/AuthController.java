package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.base.jwt.TokenProvider;
import com.ddooby.gachiillgi.base.util.CommonUtil;
import com.ddooby.gachiillgi.domain.service.MailService;
import com.ddooby.gachiillgi.domain.service.OAuthService;
import com.ddooby.gachiillgi.domain.service.UserService;
import com.ddooby.gachiillgi.interfaces.dto.request.LoginRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.MailSendRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.UserDetailInfoRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.KakaoProfileResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.TokenResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.UserRegisterResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final String logoutRedirectUrl;
    private final UserService userService;
    private final MailService mailService;
    private final OAuthService kakaoOAuthService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(@Value("${redirect-url.home}") String logoutRedirectUrl,
                          UserService userService,
                          MailService mailService,
                          @Qualifier("kakaoOAuthServiceImpl") OAuthService kakaoOAuthService,
                          TokenProvider tokenProvider,
                          AuthenticationManagerBuilder authenticationManagerBuilder) {

        this.logoutRedirectUrl = logoutRedirectUrl;
        this.userService = userService;
        this.mailService = mailService;
        this.kakaoOAuthService = kakaoOAuthService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/signup")
    public UserRegisterResponseDTO signup(@Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDto) {
        return userService.signup(userRegisterRequestDto);
    }

    @PostMapping("/signup/add-detail")
    public UserRegisterResponseDTO signupWithDetail(@RequestBody UserDetailInfoRegisterRequestDTO requestDTO) {
        return userService.signupWithDetail(requestDTO);
    }

    @PostMapping("/send-mail")

    public String sendVerificationMail(@RequestBody MailSendRequestDTO mailSendRequestDTO) { // FIXME 코드 너무 더럽;

        String email = mailSendRequestDTO.getEmail();
        String username = mailSendRequestDTO.getNickname();
        String temporaryLink = tokenProvider.createTemporaryLink(mailSendRequestDTO.getNickname());

        //TODO 유저 검증이 필요할까?

        log.debug("email = {} username = {} tempLink = {}", email, username, temporaryLink);
        String subject = "[가치일기] 안녕하세요, " + mailSendRequestDTO.getNickname() + "님! 메일인증을 완료해주세요.";
        Map<String, Object> variables = new HashMap<>();
        variables.put("nickname", mailSendRequestDTO.getNickname() + "님 환영합니다!");
        variables.put("link", "http://localhost:8080/api/auth/verify-mail/?link=" + temporaryLink);
        variables.put("location", "https://가치일기.com");
        mailService.send(subject, variables, mailSendRequestDTO.getEmail());

        return "인증 메일 전송 완료";
    }

    @GetMapping("/verify-mail")
    public RedirectView verifyVerificationMail(@RequestParam String link) { // FIXME 어떻게 정리할까..?
        String email = tokenProvider.verifyTemporaryLink(link);
        if (email != null) {
            userService.updateActivated(email);
            return new RedirectView("https://google.com");
        } else {
            return new RedirectView("/error");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> authorize(@Valid @RequestBody LoginRequestDTO loginRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        return new ResponseEntity<>(new TokenResponseDTO(token), CommonUtil.buildAuthCookie(token), HttpStatus.OK);
    }

    @GetMapping(value = "/kakao-login")
    public RedirectView loginByKakao(@RequestParam("code") String authorizationCode,
                                     @RequestParam(value = "error", required = false) String errorCode,
                                     @RequestParam(value = "error_description", required = false) String errorDescription,
                                     RedirectAttributes redirectAttributes,
                                     HttpServletResponse httpServletResponse) {

        if (StringUtils.hasText(errorCode) || StringUtils.hasText(errorDescription)) {
            throw new BizException(AuthErrorCodeEnum.KAKAO_AUTH_CANCEL);
        }

        log.debug("### authorization code : " + authorizationCode);
        KakaoProfileResponseDTO kakaoUserInfo = kakaoOAuthService.getUserInfo(kakaoOAuthService.getAccessToken(authorizationCode));
        String userEmail = kakaoUserInfo.getKakaoAccount().getEmail();

        if (userService.isUser(userEmail)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(kakaoUserInfo.getKakaoAccount().getEmail(), kakaoUserInfo.getId());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = tokenProvider.createToken(authentication);
            CommonUtil.addAuthCookie(token, httpServletResponse);

            return new RedirectView("http://localhost:3000");
        } else {
            userService.signup(kakaoUserInfo.toUserRegisterRequestDTO());
            redirectAttributes.addAttribute("email", userEmail);
            return new RedirectView("http://localhost:3000/join/agree");
        }
    }
}
