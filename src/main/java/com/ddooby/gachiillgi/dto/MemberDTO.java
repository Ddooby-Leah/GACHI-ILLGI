package com.ddooby.gachiillgi.dto;

import com.ddooby.gachiillgi.entity.Member;
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
public class MemberDTO {

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

    private Set<AuthorityDTO> authorityDtoSet;

    public static MemberDTO from(Member member) {
        if(member == null) return null;

        return MemberDTO.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .authorityDtoSet(member.getMemberAuthoritySet().stream()
                        .map(authority ->
                                AuthorityDTO.builder()
                                        .authorityName(authority.getAuthority().getAuthorityName())
                                        .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
