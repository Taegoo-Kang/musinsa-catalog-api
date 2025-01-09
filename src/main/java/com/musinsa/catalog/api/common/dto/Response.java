package com.musinsa.catalog.api.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private final String result;
    private final T body;
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
