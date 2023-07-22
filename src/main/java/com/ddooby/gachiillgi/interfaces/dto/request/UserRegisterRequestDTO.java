package com.ddooby.gachiillgi.interfaces.dto.request;

import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.interfaces.dto.response.AuthorityResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDTO {

    @NotNull
    @Email
    @Size(min = 3, max = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 10)
    private String nickname;

    private String sex;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday;

    private String profileImageUrl;

    @NotNull
    private Boolean isOAuthUser;


    @JsonIgnore
    private Set<AuthorityResponseDTO> authorityResponseDtoSet;

    public static UserRegisterRequestDTO from(User user) {
        if (user == null) return null;

        return UserRegisterRequestDTO.builder()
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
                .build();
    }
}
