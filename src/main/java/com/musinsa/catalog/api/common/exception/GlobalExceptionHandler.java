package com.musinsa.catalog.api.common.exception;

import com.musinsa.catalog.api.common.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<?>> handleNotFoundError(NotFoundException e) {
        log.error("{} [{}]을(를) 찾을 수 없습니다.", e.getTarget(), e.getTargetId());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<?>> handleValidationError(MethodArgumentNotValidException e) {
        String errorMessage = null;

        var fieldError = e.getFieldError();
        if (fieldError != null) {
            var field = fieldError.getField();
            var message = fieldError.getDefaultMessage();

            errorMessage = "[" + field + "] " + message;
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(StringUtils.hasText(errorMessage) ? errorMessage : e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleException(Exception e) {
        log.error("Unknown Error occurred:: {}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.fail(e.getMessage()));
    }
}
