package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.base.enums.UserStatusEnum;
import com.ddooby.gachiillgi.base.jwt.TokenProvider;
import com.ddooby.gachiillgi.base.util.CommonUtil;
import com.ddooby.gachiillgi.domain.service.MailService;
import com.ddooby.gachiillgi.domain.service.OAuthService;
import com.ddooby.gachiillgi.interfaces.dto.request.LoginRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.MailSendRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.UserDetailInfoRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.KakaoProfileResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.MailServiceResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(
        {
                @Sql(value = "/sql/auth-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        }
)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenProvider tokenProvider;

    @MockBean
    private MailService mailService;

    @MockBean
    private OAuthService oAuthService;

    @Test
    public void 사용자는_회원가입을_할_수있다() throws Exception {
        //given
        String email = "ddooby.doobob@kakaoenterprise.com";
        String password = "12341234";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        boolean isOAuthUser = false;
        String sex = "남자";
        String nickname = "ddooby";

        UserRegisterRequestDTO requestDTO = UserRegisterRequestDTO.builder()
                .email(email)
                .password(password)
                .birthday(birthday)
                .isOAuthUser(isOAuthUser)
                .sex(sex)
                .nickname(nickname)
                .build();


        //when
        //then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonUtil.ObjectToJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents.email").value(email))
                .andExpect(jsonPath("$.contents.isOAuthUser").value(isOAuthUser))
                .andExpect(jsonPath("$.contents.sex").value(sex))
                .andExpect(jsonPath("$.contents.nickname").value(nickname));
    }

    @Test
    public void 이메일_형식이_아니면_400_응답을_받는다() throws Exception {
        //given
        String email = "ddasdasdsadcom";
        String password = "12341234";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        boolean isOAuthUser = false;
        String sex = "남자";
        String nickname = "ddooby";

        UserRegisterRequestDTO requestDTO = UserRegisterRequestDTO.builder()
                .email(email)
                .password(password)
                .birthday(birthday)
                .isOAuthUser(isOAuthUser)
                .sex(sex)
                .nickname(nickname)
                .build();


        //when
        //then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonUtil.ObjectToJsonString(requestDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("@Valid Error"));
    }

    @Test
    public void OAUTH_유저는_추가정보를_입력할_수_있습니다() throws Exception {
        //given
        String email = "ddooby1@kakaoenterprise.com";
        LocalDate birthday = LocalDate.of(2022, 1, 1);
        String sex = "남자";
        String nickname = "ddooby";

        UserDetailInfoRegisterRequestDTO requestDTO = UserDetailInfoRegisterRequestDTO.builder()
                .email(email)
                .sex(sex)
                .nickname(nickname)
                .birthday(birthday)
                .build();

        mockMvc.perform(post("/api/auth/signup/add-detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonUtil.ObjectToJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents.email").value(email))
                .andExpect(jsonPath("$.contents.nickname").value(nickname))
                .andExpect(jsonPath("$.contents.sex").value(sex))
                .andExpect(jsonPath("$.contents.userStatusEnum").value(UserStatusEnum.ACTIVATED.getStatus()));
    }

    @Test
    public void 인증_이메일_발송을_할_수_있습니다() throws Exception {
        //given
        String email = "ddooby1@kakaoenterprise.com";
        String nickname = "ddooby";

        MailSendRequestDTO requestDTO = MailSendRequestDTO.builder()
                .email(email)
                .nickname(nickname)
                .build();

        MailServiceResponseDTO responseDTO = MailServiceResponseDTO.builder()
                .messageId("123")
                .build();

        BDDMockito.when(mailService.send(any(), any(), any()))
                .thenReturn(responseDTO);

        //when
        //then
        mockMvc.perform(post("/api/auth/send-mail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonUtil.ObjectToJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("1"))
                .andExpect(jsonPath("$.contents.messageId").value("123"));
    }

    @Test
    public void 이메일_인증_링크를_통해_인증되면_홈으로_리아디렉트_됩니다() throws Exception {
        //given
        String email = "ddooby1@kakaoenterprise.com";
        String temporaryLink = tokenProvider.createTemporaryLink(email);

        mockMvc.perform(get("/api/auth/verify-mail")
                        .param("link", temporaryLink))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost:3000"));
    }

    @Test
    public void 유저는_로그인_할_수_있다() throws Exception {
        //given
        String email = "ddooby2@kakaoenterprise.com";
        String password = "1234";

        LoginRequestDTO requestDTO = LoginRequestDTO.builder()
                .email(email)
                .password(password)
                .build();

        //when then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonUtil.ObjectToJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contents.token").isNotEmpty())
                .andExpect(jsonPath("$.contents.tokenExpireTime").isNumber());
    }

    @Test
    public void 카카오_계정을_이용해서_회원가입_하면_추가정보_입력창으로_리다이렉트_된다() throws Exception {

        //given
        String id = "1234";
        String email = "ddooby1@kakaoenterprise.com";
        KakaoProfileResponseDTO responseDTO = KakaoProfileResponseDTO.builder()
                .id(id)
                .kakaoAccount(KakaoProfileResponseDTO.KakaoAccount.builder()
                        .email(email)
                        .profile(KakaoProfileResponseDTO.KakaoAccount.Profile.builder()
                                .profileImageUrl("profile")
                                .build())
                        .build())
                .properties(KakaoProfileResponseDTO.Properties.builder()
                        .nickname("ddooby")
                        .profileImage("profile")
                        .build())
                .build();

        BDDMockito.when(oAuthService.getAccessToken(any())).thenReturn("token");
        BDDMockito.when(oAuthService.getUserInfo(any())).thenReturn(responseDTO);


        //when then
        mockMvc.perform(get("/api/auth/kakao-login")
                        .param("code", "code"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost:3000/join/agree?email=" + email));
    }

    @Test
    public void 카카오_계정을_이용해서_로그인_하면_추가정보_홈으로_리다이렉트_된다() throws Exception {

        //given
        String id = "1234";
        String email = "ddooby2@kakaoenterprise.com";
        KakaoProfileResponseDTO responseDTO = KakaoProfileResponseDTO.builder()
                .id(id)
                .kakaoAccount(KakaoProfileResponseDTO.KakaoAccount.builder()
                        .email(email)
                        .profile(KakaoProfileResponseDTO.KakaoAccount.Profile.builder()
                                .profileImageUrl("profile")
                                .build())
                        .build())
                .properties(KakaoProfileResponseDTO.Properties.builder()
                        .nickname("ddooby")
                        .profileImage("profile")
                        .build())
                .build();

        BDDMockito.when(oAuthService.getAccessToken(any())).thenReturn("token");
        BDDMockito.when(oAuthService.getUserInfo(any())).thenReturn(responseDTO);

        //when then
        mockMvc.perform(get("/api/auth/kakao-login")
                        .param("code", "code"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("location", "http://localhost:3000"))
                .andExpect(cookie().exists("accessToken"));
    }

    @Test
    public void 카카오_에서_에러코드를_파라미터로_넘겨주면_예외가_발생한다() throws Exception {

        //given
        //when then
        mockMvc.perform(get("/api/auth/kakao-login")
                        .param("error", "error"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("-1"))
                .andExpect(jsonPath("$.longMessage").value("카카오 인증 과정 중 취소처리 되었습니다."));
    }
}