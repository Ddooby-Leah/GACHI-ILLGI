package com.ddooby.gachiillgi.interfaces.dto.request;

import com.ddooby.gachiillgi.domain.entity.User;
import com.ddooby.gachiillgi.interfaces.dto.response.AuthorityResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    @JsonIgnore
    private Set<AuthorityResponseDTO> authorityResponseDtoSet;

    public static UserRequestDTO from(User user) {
        if(user == null) return null;

        return UserRequestDTO.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorityResponseDtoSet(user.getUserAuthoritySet().stream()
                        .map(authority ->
                                AuthorityResponseDTO.builder()
                                        .authorityName(authority.getAuthority().getAuthorityName())
                                        .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
