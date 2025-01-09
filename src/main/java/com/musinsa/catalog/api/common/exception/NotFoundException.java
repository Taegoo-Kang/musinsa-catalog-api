package com.musinsa.catalog.api.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String target;
    private final Long targetId;

    public NotFoundException(String target, Long targetId) {
        super(target + "정보를 찾을 수 없습니다.");
        this.target = target;
        this.targetId = targetId;
    }
}
