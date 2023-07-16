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
    private String nickname;

    @NotNull
    private String email;



//    @NotNull
//    @Size(min = 3, max = 50)
//    private String nickname;
}
