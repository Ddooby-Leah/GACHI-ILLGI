package com.ddooby.gachiillgi.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

@RequiredArgsConstructor
public class ErrorDTO {
    private final int status;
    private final String message;
    private final List<FieldError> fieldErrors = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void addFieldError(String objectName, String path, String message) {
        FieldError error = new FieldError(objectName, path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
