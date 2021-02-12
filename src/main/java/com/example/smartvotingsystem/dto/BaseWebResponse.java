package com.example.smartvotingsystem.dto;

import com.example.smartvotingsystem.exception.ErrorCode;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseWebResponse<T> {
    private ErrorCode errorCode;
    private T data;

    public static <T> BaseWebResponse<T> successWithData(T data) {
        return BaseWebResponse.<T>builder()
                .data(data)
                .build();
    }

    public static BaseWebResponse error(ErrorCode errorCode) {
        return BaseWebResponse.builder()
                .errorCode(errorCode)
                .build();
    }

    public BaseWebResponse mapToResponse(T data) {
        return BaseWebResponse.builder().data(data)
                .build();
    }
}

