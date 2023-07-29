package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.base.jwt.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(
        {
                @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        }
)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private String token;
    private String adminToken;

    @BeforeEach
    void createToken() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("ddooby.doobob@kakaoenterprise.com", "1234");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        UsernamePasswordAuthenticationToken adminAuthToken =
                new UsernamePasswordAuthenticationToken("ddooby.doobob1@kakaoenterprise.com", "1234");
        Authentication adminAuthentication = authenticationManagerBuilder.getObject().authenticate(adminAuthToken);

        this.token = tokenProvider.createToken(authentication);
        this.adminToken = tokenProvider.createToken(adminAuthentication);
    }

    @Test
    public void 사용자는_내_정보를_볼_수있다() throws Exception {
        //given
        //when then
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("accessToken", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.contents.email").value("ddooby.doobob@kakaoenterprise.com"))
                .andDo(print());
    }

    @Test
    public void 일반유저는_다른_유저의_정보를_조회하면_DENIED를_응답_받는다() throws Exception {
        //given
        //when then
        mockMvc.perform(get("/api/users/ddooby.doobob@kakaoenterprise.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("accessToken", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("-2"))
                .andExpect(jsonPath("$.longMessage").value("Access Denied"))
                .andDo(print());
    }

    @Test
    public void 관리자는_다른_유저의_정보를_조회할_수_있다() throws Exception {
        //given
        //when then
        mockMvc.perform(get("/api/users/ddooby.doobob@kakaoenterprise.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("accessToken", adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.contents.email").value("ddooby.doobob@kakaoenterprise.com"))
                .andDo(print());
    }

}