package com.ddooby.gachiillgi.interfaces.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultErrorResponseDTO {

    private int code = -1;
    private String longMessage;
    private String shortMessage;
    private String errorCode;

    private DefaultErrorResponseDTO() {}

    public static DefaultErrorResponseDTO systemError (String longMessage , String shortMessage) {
        DefaultErrorResponseDTO defaultErrorResponseDTO = new DefaultErrorResponseDTO();
        defaultErrorResponseDTO.code = -2;
        defaultErrorResponseDTO.errorCode = "";
        defaultErrorResponseDTO.longMessage = longMessage;
        defaultErrorResponseDTO.shortMessage = shortMessage;
        return defaultErrorResponseDTO;
    }

    public static DefaultErrorResponseDTO error (String errorCode , String longMessage , String shortMessage) {
        DefaultErrorResponseDTO defaultErrorResponseDTO = new DefaultErrorResponseDTO();
        defaultErrorResponseDTO.code = -1;
        defaultErrorResponseDTO.errorCode = errorCode;
        defaultErrorResponseDTO.longMessage = longMessage;
        defaultErrorResponseDTO.shortMessage = shortMessage;
        return defaultErrorResponseDTO;
    }

    public static DefaultErrorResponseDTO error (String longMessage , String shortMessage) {
        DefaultErrorResponseDTO defaultErrorResponseDTO = new DefaultErrorResponseDTO();
        defaultErrorResponseDTO.code = -1;
        defaultErrorResponseDTO.errorCode = "";
        defaultErrorResponseDTO.longMessage = longMessage;
        defaultErrorResponseDTO.shortMessage = shortMessage;
        return defaultErrorResponseDTO;
    }
}
