package com.ddooby.gachiillgi.domain.service;

import com.ddooby.gachiillgi.base.enums.UserStatusEnum;
import com.ddooby.gachiillgi.base.enums.exception.AuthErrorCodeEnum;
import com.ddooby.gachiillgi.base.enums.exception.UserErrorCodeEnum;
import com.ddooby.gachiillgi.base.exception.BizException;
import com.ddooby.gachiillgi.interfaces.dto.request.UserDetailInfoRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.request.UserRegisterRequestDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.UserRegisterResponseDTO;
import com.ddooby.gachiillgi.interfaces.dto.response.UserUpdateResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;

@SpringBootTest
@SqlGroup(
        {
                @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        }
)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void 유저_아이디를_생성할_수_있다() {
        //given
        UserRegisterRequestDTO requestDTO = UserRegisterRequestDTO.builder()
                .email("ddooby.doobob@kakaoenterprise.com")
                .password("1234")
                .isOAuthUser(false)
                .sex("man")
                .nickname("ddooby")
                .birthday(LocalDate.of(2022, 03, 12))
                .profileImageUrl(null)
                .build();

        //when
        UserRegisterResponseDTO responseDTO = userService.signup(requestDTO);

        //then
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getEmail()).isEqualTo(requestDTO.getEmail());
        Assertions.assertThat(responseDTO.getAuthorityResponseDtoSet().size()).isEqualTo(1);
    }

    @Test
    void 외부_OAuth연동_유저가_아닌경우_회원가입_요청시_이메일_중복이면_DUPLICATE_EMAIL_예외가_발생한다() {
        //given
        UserRegisterRequestDTO requestDTO = UserRegisterRequestDTO.builder()
                .email("ddooby@kakaoenterprise.com")
                .password("1234")
                .isOAuthUser(false)
                .sex("man")
                .nickname("ddooby")
                .birthday(LocalDate.of(2022, 03, 12))
                .profileImageUrl(null)
                .build();

        //when then
        Assertions.assertThatThrownBy(() -> userService.signup(requestDTO))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.DUPLICATE_EMAIL.getLongMessage());
    }

    @Test
    void 외부_OAuth연동_유저인_경우_회원가입시_이메일이_중복이면_예외가_발생하지_않는다() {
        //given
        UserRegisterRequestDTO requestDTO = UserRegisterRequestDTO.builder()
                .email("ddooby1@kakaoenterprise.com")
                .password("1234")
                .isOAuthUser(true)
                .sex("man")
                .nickname("ddooby")
                .birthday(LocalDate.of(2022, 03, 12))
                .profileImageUrl(null)
                .build();

        //when
        UserRegisterResponseDTO responseDTO = userService.signup(requestDTO);

        //then
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getEmail()).isEqualTo(requestDTO.getEmail());
    }

    @Test
    void 외부_OAuth연동_유저는_추가정보를_입력하면_ACTIVATED_상태로_변경된다() {
        //given
        String email = "ddooby1@kakaoenterprise.com";
        LocalDate birthday = LocalDate.of(2023, 02, 01);
        String sex = "남자";
        String nickname = "뚜비";

        UserDetailInfoRegisterRequestDTO requestDTO = UserDetailInfoRegisterRequestDTO.builder()
                .email(email)
                .birthday(birthday)
                .sex(sex)
                .nickname(nickname)
                .build();

        //when
        UserRegisterResponseDTO responseDTO = userService.signupWithDetail(requestDTO);

        //then
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getEmail()).isEqualTo(email);
        Assertions.assertThat(responseDTO.getNickname()).isEqualTo(nickname);
        Assertions.assertThat(responseDTO.getUserStatusEnum()).isEqualTo(UserStatusEnum.ACTIVATED);
    }

    @Test
    void 외부_OAuth연동_유저의_추가정보_입력시_잘못된_이메일은_예외가_발생한다() {
        //given
        String email = "ddoobasdsasdy1@kakaoenterprise.com";
        LocalDate birthday = LocalDate.of(2023, 02, 01);
        String sex = "남자";
        String nickname = "뚜비";

        UserDetailInfoRegisterRequestDTO requestDTO = UserDetailInfoRegisterRequestDTO.builder()
                .email(email)
                .birthday(birthday)
                .sex(sex)
                .nickname(nickname)
                .build();

        //when then
        Assertions.assertThatThrownBy(() -> userService.signupWithDetail(requestDTO))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }

    @Test
    void 외부_OAuth연동_유저의_추가정보_입력시_이미_ACTIVATED상태이면_예외가_발생한다() {
        //given
        String email = "ddooby2@kakaoenterprise.com";
        LocalDate birthday = LocalDate.of(2023, 02, 01);
        String sex = "남자";
        String nickname = "뚜비";

        UserDetailInfoRegisterRequestDTO requestDTO = UserDetailInfoRegisterRequestDTO.builder()
                .email(email)
                .birthday(birthday)
                .sex(sex)
                .nickname(nickname)
                .build();

        //when then
        Assertions.assertThatThrownBy(() -> userService.signupWithDetail(requestDTO))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(AuthErrorCodeEnum.ALREADY_COMPLETE_ADD_DETAIL.getLongMessage());
    }

    @Test
    void 유저_상태를_ACTIVATED_상태로_변경할_수_있다() {
        //given
        String email = "ddooby1@kakaoenterprise.com";

        //when
        UserUpdateResponseDTO responseDTO = userService.updateActivated(email);

        //then
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getEmail()).isEqualTo(email);
        Assertions.assertThat(responseDTO.getUserStatusEnum()).isEqualTo(UserStatusEnum.ACTIVATED);
    }

    @Test
    void 유저_상태를_변경할때_유저가_존재하지_않으면_예외_발생() {
        //given
        String invalidEmail = "asdsadsada@asdsadad.com";

        //when then
        Assertions.assertThatThrownBy(() -> userService.updateActivated(invalidEmail))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }

    @Test
    void 유저_상태를_변경할때_유저가_이미_ACTIVATED_상태이면_예외_발생() {
        //given
        String email = "ddooby2@kakaoenterprise.com";

        //when then
        Assertions.assertThatThrownBy(() -> userService.updateActivated(email))
                .isInstanceOf(BizException.class)
                .hasMessageContaining(AuthErrorCodeEnum.ALREADY_COMPLETE_VERIFICATION.getLongMessage());
    }

    @Test
    void 이메일로_유저_정보를_가져올_수_있다() {
        //given
        String email = "ddooby2@kakaoenterprise.com";

        //when
        UserRegisterResponseDTO userWithAuthorities = userService.getUserWithAuthorities(email);

        //then
        Assertions.assertThat(userWithAuthorities).isNotNull();
        Assertions.assertThat(userWithAuthorities.getNickname()).isEqualTo("ddooby");
        Assertions.assertThat(userWithAuthorities.getEmail()).isEqualTo(email);
        Assertions.assertThat(userWithAuthorities.getAuthorityResponseDtoSet()).isEmpty();
    }

    @Test
    void 잘못된_이메일로_유저_정보를_가져올_시_NULL이_반환된다() {
        //given
        String invalidEmail = "asdasddsadsad@131313asd.com";

        //when
        UserRegisterResponseDTO responseDTO = userService.getUserWithAuthorities(invalidEmail);

        //then
        Assertions.assertThat(responseDTO).isNull();
    }

    @Test
    @WithMockUser(username = "ddooby2@kakaoenterprise.com")
    void 현재_Security_Context에_등록된_유저정보를_가져올_수_있다() {
        //given
        //when
        UserRegisterResponseDTO responseDTO = userService.getMyUserWithAuthorities();

        //then
        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.getEmail()).isEqualTo("ddooby2@kakaoenterprise.com");
    }

    @Test
    @WithMockUser(username = "asdadsad@adsadad.com")
    void 현재_Security_Context에_등록된_유저정보가_이상하면_예외가_발생한다() {
        //when then
        Assertions.assertThatThrownBy(() -> userService.getMyUserWithAuthorities())
                .isInstanceOf(BizException.class)
                .hasMessageContaining(UserErrorCodeEnum.USER_NOT_FOUND.getLongMessage());
    }

    @Test
    void 이메일로_활성화된_회원이_맞는지_확인할_수_있다() {
        //given
        String email = "ddooby2@kakaoenterprise.com";

        //when
        boolean isUser = userService.isUser(email);

        //then
        Assertions.assertThat(isUser).isTrue();
    }

    @Test
    void 유저확인시_활성화된_유저가_아니면_false를_반환한다() {
        //given
        String email = "ddooby1@kakaoenterprise.com";

        //when
        boolean isUser = userService.isUser(email);

        //then
        Assertions.assertThat(isUser).isFalse();
    }


}