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
public class UserRequestDTO {

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

    @NotNull
    @Size(max = 10)
    private String name;

    @NotNull
    private String sex;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalDateTime registDate;

    @JsonIgnore
    private Set<AuthorityResponseDTO> authorityResponseDtoSet;

    public static UserRequestDTO from(User user) {
        if(user == null) return null;

        return UserRequestDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .sex(user.getSex())
                .birthday(user.getBirthday())
                .name(user.getName())
                .authorityResponseDtoSet(user.getUserAuthoritySet().stream()
                        .map(authority ->
                                AuthorityResponseDTO.builder()
                                        .authorityName(authority.getAuthority().getAuthorityName())
                                        .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
