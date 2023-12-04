package com.gotcha.vote.polling.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.TeamName;
import com.gotcha.vote.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LeaderVoteTest {
    @Test
    @DisplayName("본인에게 투표할 때 예외가 발생한다.")
    void 본인투표는_예외발생하기() {
        // given
        User 종미 = User.builder().name("종미").loginId("종미").email("test@naver.com").pwd("test").partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();

        // when & then
        assertThatThrownBy(() -> {
            LeaderVote.builder().voter(종미).candidate(종미).build();
        }).isInstanceOf(AppException.class)
                .hasMessage(ErrorCode.SELF_VOTE.getMessage());

    }
}