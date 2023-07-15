package com.ddooby.gachiillgi.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponseDTO {
    private final int status;
    private final String message;
    private final List<FieldError> fieldErrors = new ArrayList<>();

    public void addFieldError(String objectName, String path, String message) {
        FieldError error = new FieldError(objectName, path, message);
        fieldErrors.add(error);
    }
}
