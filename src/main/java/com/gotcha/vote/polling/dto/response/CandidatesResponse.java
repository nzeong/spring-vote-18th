package com.gotcha.vote.polling.dto.response;

import com.gotcha.vote.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidatesResponse {

    private Long id;
    private String name;

    @Builder
    public CandidatesResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CandidatesResponse from(User user) {
        return CandidatesResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
