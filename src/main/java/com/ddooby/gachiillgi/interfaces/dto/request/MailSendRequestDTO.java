package com.ddooby.gachiillgi.interfaces.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MailSendRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String email;

//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotNull
//    @Size(min = 3, max = 100)
//    private String password;
//
//    @NotNull
//    @Size(min = 3, max = 50)
//    private String nickname;
}
