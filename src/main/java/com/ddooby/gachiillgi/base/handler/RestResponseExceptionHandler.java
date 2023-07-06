package com.ddooby.gachiillgi.base.handler;

import com.ddooby.gachiillgi.base.exception.DuplicateMemberException;
import com.ddooby.gachiillgi.base.exception.NotFoundMemberException;
import com.ddooby.gachiillgi.dto.ErrorDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = { DuplicateMemberException.class })
    protected ErrorDTO conflict(RuntimeException ex, WebRequest request) {
        return new ErrorDTO(CONFLICT.value(), ex.getMessage());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = { NotFoundMemberException.class, AccessDeniedException.class })
    protected ErrorDTO forbidden(RuntimeException ex, WebRequest request) {
        return new ErrorDTO(FORBIDDEN.value(), ex.getMessage());
    }
}
