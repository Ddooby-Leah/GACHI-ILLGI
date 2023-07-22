package com.ddooby.gachiillgi.interfaces.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
public class UserDetailInfoRegisterRequestDTO {
    @NotNull
    private final String email;

    @NotNull
    @Size(min = 3, max = 10)
    private final String nickname;

    @NotNull
    private final String sex;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate birthday;
}
