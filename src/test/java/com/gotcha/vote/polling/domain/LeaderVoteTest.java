package com.gotcha.vote.polling.domain;

import static com.gotcha.vote.environ.TestUser.종미;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LeaderVoteTest {
    @Test
    @DisplayName("본인에게 투표할 때 예외가 발생한다.")
    void 본인투표는_예외발생하기() {
        // when & then
        assertThatThrownBy(() -> {
            LeaderVote.builder().voter(종미).candidate(종미).build();
        }).isInstanceOf(AppException.class)
                .hasMessage(ErrorCode.SELF_VOTE.getMessage());

    }
}