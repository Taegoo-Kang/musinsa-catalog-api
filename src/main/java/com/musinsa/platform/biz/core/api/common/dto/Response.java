package com.musinsa.platform.biz.core.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    @Schema(description = "성공/실패 여부", defaultValue = "성공")
    private final String result;
    private final T body;
    @Schema(description = "실패 사유", defaultValue = "에러메시지")
    private final String errorMessage;

    public static <T> Response<T> success(T body) {
        return Response.<T>builder()
                .result("성공")
                .body(body)
                .build();
    }

    public static <T> Response<T> fail(String errorMessage) {
        return Response.<T>builder()
                .result("실패")
                .errorMessage(errorMessage)
                .build();
    }
}
