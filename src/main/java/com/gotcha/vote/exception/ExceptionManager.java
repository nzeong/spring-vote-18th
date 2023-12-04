package com.gotcha.vote.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode().name() + " " + e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(ErrorCode.NO_PARAMETER.getHttpStatus())
                .body(ErrorCode.NO_PARAMETER.name() + " " + ex.getParameterName() + ErrorCode.NO_PARAMETER.getMessage());
    }
}
