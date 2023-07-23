package com.ddooby.gachiillgi.interfaces.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class DefaultResponseDTO {

    private final String code = "1";
    private final String longMessage = "SUCCESS";
    private final String shortMessage = "SUCCESS";
    private Object contents;

    // 팩토리 메서드
    public static DefaultResponseDTO create(Object contents) {
        DefaultResponseDTO dto = new DefaultResponseDTO();
        dto.setContents(contents);
        return dto;
    }

    // Setter 메서드
    public void setContents(Object contents) {
        this.contents = contents;
    }
}
