package com.ddooby.gachiillgi.interfaces.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MailSendDTO {

    @NotNull
    private String username;

    @NotNull
    private String email;

//    @NotNull
//    @Size(min = 3, max = 50)
//    private String username;
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
