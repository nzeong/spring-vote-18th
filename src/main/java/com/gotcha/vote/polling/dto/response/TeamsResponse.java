package com.gotcha.vote.polling.dto.response;

import com.gotcha.vote.user.domain.TeamName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamsResponse {

    private String name;

    public TeamsResponse(final String name) {
        this.name = name;
    }

    public static TeamsResponse from(final TeamName team) {
        return new TeamsResponse(team.getValue());
    }
}
