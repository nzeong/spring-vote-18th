package com.gotcha.vote.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequest {
    @NotNull(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String pwd;

    @Builder
    public UserLoginRequest(String loginId, String pwd) {
        this.loginId = loginId;
        this.pwd = pwd;
    }
}