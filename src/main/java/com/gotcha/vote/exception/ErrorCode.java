package com.gotcha.vote.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USERID_DUPICATED(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    USEREMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");

    private HttpStatus httpStatus;
    private String message;
}
