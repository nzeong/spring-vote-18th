package com.gotcha.vote.polling.dto.response;

import com.gotcha.vote.user.domain.TeamName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamsResponse {

    private TeamName team;
    private Long count;

    public TeamsResponse(final TeamName team, final Long count) {
        this.team = team;
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        TeamsResponse other = (TeamsResponse) o;
        if(other.getTeam().equals(team) && other.getCount().equals(count)) {
            return true;
        }
        return false;
    }
}
