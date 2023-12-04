package com.gotcha.vote.polling.domain;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import com.gotcha.vote.user.domain.TeamName;
import com.gotcha.vote.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User voter;

    @Column(nullable = false)
    private TeamName team;

    @Builder
    public TeamVote(final User voter, final TeamName team) {
        validate(voter, team);
        this.voter = voter;
        this.team = team;
    }

    private void validate(final User voter, final TeamName team) {
        if(team.equals(voter.getTeamName())) {
            throw new AppException(ErrorCode.SELF_VOTE);
        }
    }
}
