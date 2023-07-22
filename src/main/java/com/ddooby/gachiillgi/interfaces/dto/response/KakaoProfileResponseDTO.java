package com.ddooby.gachiillgi.interfaces.dto.response;

import com.ddooby.gachiillgi.interfaces.dto.request.UserRegisterRequestDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class KakaoProfileResponseDTO {

    @JsonProperty("id")
    private final String id;

    @JsonProperty("connected_at")
    private final String connectedAt;

    @JsonProperty("properties")
    private final Properties properties;

    @JsonProperty("kakao_account")
    private final KakaoAccount kakaoAccount;

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Properties { //(1)
        private final String nickname;

        @JsonProperty("profile_image")
        private final String profileImage; // 이미지 경로 필드1

        @JsonProperty("thumbnail_image")
        private final String thumbnailImage;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class KakaoAccount { //(2)
        @JsonProperty("profile_nickname_needs_agreement")
        private final Boolean profileNicknameNeedsAgreement;

        @JsonProperty("profile_image_needs_agreement")
        private final Boolean profileImageNeedsAgreement;

        @JsonProperty("profile")
        private final Profile profile;

        @JsonProperty("has_email")
        private final Boolean hasEmail;

        @JsonProperty("email_needs_agreement")
        private final Boolean emailNeedsAgreement;

        @JsonProperty("is_email_valid")
        private final Boolean isEmailValid;

        @JsonProperty("is_email_verified")
        private final Boolean isEmailVerified;

        @JsonProperty("email")
        private final String email;

        @Getter
        @ToString
        @AllArgsConstructor
        public static class Profile {

            @JsonProperty("nickname")
            private final String nickname;

            @JsonProperty("thumbnail_image_url")
            private final String thumbnailImageUrl;

            @JsonProperty("profile_image_url")
            private final String profileImageUrl;

            @JsonProperty("is_default_image")
            private final Boolean isDefaultImage;
        }
    }

    public UserRegisterRequestDTO toUserRegisterRequestDTO() {
        return UserRegisterRequestDTO.builder()
                .email(this.getKakaoAccount().getEmail().strip())
                .nickname(this.getProperties().getNickname())
                .password(this.getId())
                .profileImageUrl(this.getKakaoAccount().getProfile().getProfileImageUrl())
                .isOAuthUser(true)
                .build();
    }
}
