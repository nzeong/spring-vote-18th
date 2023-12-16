package com.gotcha.vote.polling.domain;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import com.gotcha.vote.user.domain.User;
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
public class LeaderVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User candidate;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User voter;

    @Builder
    public LeaderVote(final User candidate, final User voter) {
        validateSelfVote(candidate, voter);
        this.candidate = candidate;
        this.voter = voter;
    }

    private void validateSelfVote(final User candidate, final User voter) {
        if(candidate.equals(voter)) {
            throw new AppException(ErrorCode.SELF_VOTE);
        }
    }
}
