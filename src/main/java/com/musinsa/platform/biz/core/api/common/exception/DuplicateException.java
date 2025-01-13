package com.musinsa.platform.biz.core.api.common.exception;

import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException {

    private final String target;
    private final String key;

    public DuplicateException(String target, String key) {
        super("이미 같은 " + target + "[" + key + "]이(가) 존재합니다.");
        this.target = target;
        this.key = key;
    }
}
