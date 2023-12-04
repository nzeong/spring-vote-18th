package com.gotcha.vote.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USERID_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
    USERID_DUPICATED(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    USEREMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 비밀번호입니다."),

    SECURITY_ERROR(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT 토큰이 잘못되었습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),

    DUPLICATED_VOTE(HttpStatus.CONFLICT, "이미 투표한 사용자입니다."),
    INVALID_VOTE(HttpStatus.BAD_REQUEST, "다른 파트에 투표할 수 없습니다."),
    SELF_VOTE(HttpStatus.BAD_REQUEST, "본인과 본인 팀에게는 투표할 수 없습니다."),
    NO_PARAMETER(HttpStatus.BAD_REQUEST, " 파라미터가 없습니다.");

    private HttpStatus httpStatus;
    private String message;
}
