package com.gotcha.vote.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponse {
    private String accessToken;

    @Builder
    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static TokenResponse from(String accessToken) {
        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}