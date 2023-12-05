package com.gotcha.vote.polling.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LeaderVoteRequest {
    private Long candidateId;

    public LeaderVoteRequest(Long candidateId) {
        this.candidateId = candidateId;
    }
}
