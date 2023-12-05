package com.gotcha.vote.polling.dto.request;

import com.gotcha.vote.user.domain.TeamName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamVoteRequest {
    private TeamName teamName;

    public TeamVoteRequest(TeamName teamName) {
        this.teamName = teamName;
    }
}
