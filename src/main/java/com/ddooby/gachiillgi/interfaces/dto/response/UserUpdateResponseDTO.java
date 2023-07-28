package com.ddooby.gachiillgi.interfaces.dto.response;

import com.ddooby.gachiillgi.base.enums.UserStatusEnum;
import com.ddooby.gachiillgi.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateResponseDTO {
    private String email;

    private String password;

    private String nickname;

    private String sex;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday;

    private String profileImageUrl;

    private Boolean isOAuthUser;

    private UserStatusEnum userStatusEnum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    private Set<AuthorityResponseDTO> authorityResponseDtoSet;

    public static UserUpdateResponseDTO from(User user) {
        if (user == null) return null;

        return UserUpdateResponseDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .sex(user.getSex())
                .birthday(user.getBirthday())
                .authorityResponseDtoSet(user.getUserAuthoritySet().stream()
                        .map(authority ->
                                AuthorityResponseDTO.builder()
                                        .authorityName(authority.getAuthority().getAuthorityName())
                                        .build())
                        .collect(Collectors.toSet()))
                .isOAuthUser(user.isOAuthUser())
                .userStatusEnum(user.getActivated())
                .createAt(user.getCreatedAt())
                .build();
    }

}
