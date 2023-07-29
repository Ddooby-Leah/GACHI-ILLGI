package com.ddooby.gachiillgi.interfaces.controller;

import com.ddooby.gachiillgi.base.jwt.TokenProvider;
import com.ddooby.gachiillgi.base.util.CommonUtil;
import com.ddooby.gachiillgi.interfaces.dto.request.FollowRequestDTO;
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

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(
        {
                @Sql(value = "/sql/follow-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        }
)
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private String token;

    @BeforeEach
    void createToken() {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("ddooby.doobob@kakaoenterprise.com", "1234");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        this.token = tokenProvider.createToken(authentication);
    }

    @Test
    public void 사용자는_다른_사용자를_팔로우할_수_있다() throws Exception {
        //given
        String follower = "ddooby.doobob@kakaoenterprise.com";
        String followee = "ddooby.doobob1@kakaoenterprise.com";

        FollowRequestDTO requestDTO = FollowRequestDTO.builder()
                .followerEmail(follower)
                .followeeEmail(followee)
                .build();

        //when then
        mockMvc.perform(post("/api/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonUtil.ObjectToJsonString(requestDTO))
                        .cookie(new Cookie("accessToken", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longMessage").value("SUCCESS"))
                .andDo(print());
    }

    @Test
    public void 사용자는_다른_사용자를_언팔로우할_수_있다() throws Exception {
        //given
        String follower = "follower11@abc.com";
        String followee = "followee11@abc.com";

        FollowRequestDTO requestDTO = FollowRequestDTO.builder()
                .followerEmail(follower)
                .followeeEmail(followee)
                .build();

        //when then
        mockMvc.perform(delete("/api/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonUtil.ObjectToJsonString(requestDTO))
                        .cookie(new Cookie("accessToken", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longMessage").value("SUCCESS"))
                .andDo(print());
    }

    @Test
    public void 사용자는_해당_이메일의_팔로워들을_확인할_수_있다() throws Exception {
        //given
        String followee = "followee11@abc.com";

        //when then
        mockMvc.perform(get("/api/follow")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userEmail", followee)
                        .cookie(new Cookie("accessToken", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longMessage").value("SUCCESS"))
                .andExpect(jsonPath("$.contents.list").isArray())
                .andDo(print());
    }

}