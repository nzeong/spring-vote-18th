package com.gotcha.vote.polling.dto.request;

import com.gotcha.vote.user.domain.TeamName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamVoteRequest {
    @Schema(allowableValues = {"sharemid", "localmood", "sniff", "ready", "gotcha"})
    private TeamName teamName;

    public TeamVoteRequest(TeamName teamName) {
        this.teamName = teamName;
    }
}
