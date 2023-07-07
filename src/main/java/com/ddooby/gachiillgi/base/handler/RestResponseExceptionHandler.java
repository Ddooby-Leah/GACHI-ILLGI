package com.ddooby.gachiillgi.base.handler;

import com.ddooby.gachiillgi.base.exception.DuplicateMemberException;
import com.ddooby.gachiillgi.base.exception.InvalidTokenException;
import com.ddooby.gachiillgi.base.exception.NotFoundMemberException;
import com.ddooby.gachiillgi.dto.ErrorDTO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = { DuplicateMemberException.class })
    protected ErrorDTO conflict(RuntimeException ex) {
        return new ErrorDTO(CONFLICT.value(), ex.getMessage());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = { InvalidTokenException.class })
    protected ErrorDTO unauthorized(RuntimeException ex) {
        return new ErrorDTO(UNAUTHORIZED.value(), ex.getMessage());
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = { NotFoundMemberException.class, AccessDeniedException.class })
    protected ErrorDTO forbidden(RuntimeException ex) {
        return new ErrorDTO(FORBIDDEN.value(), ex.getMessage());
    }
}
