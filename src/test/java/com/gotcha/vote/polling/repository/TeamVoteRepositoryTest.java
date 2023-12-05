package com.gotcha.vote.polling.repository;

import static com.gotcha.vote.environ.TestUser.윤정;
import static com.gotcha.vote.environ.TestUser.은비;
import static com.gotcha.vote.environ.TestUser.종미;
import static com.gotcha.vote.environ.TestUser.지혜;
import static org.junit.jupiter.api.Assertions.*;

import com.gotcha.vote.environ.TestRepository;
import com.gotcha.vote.polling.domain.TeamVote;
import com.gotcha.vote.polling.dto.response.TeamsResponse;
import com.gotcha.vote.user.domain.TeamName;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Slf4j
@DataJpaTest
@Import(TestRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamVoteRepositoryTest {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TeamVoteRepository teamVoteRepository;

    @Test
    @DisplayName("득표 순으로 표를 받은 팀을 정렬한다.")
    void 표를_받은_팀_정렬하기() {
        // given
        testRepository.저장하기(종미, 윤정, 지혜, 은비);

        TeamVote 종미의투표 = TeamVote.builder().voter(종미).team(TeamName.SNIFF).build();
        TeamVote 윤정의투표 = TeamVote.builder().voter(윤정).team(TeamName.SNIFF).build();
        TeamVote 지혜의투표 = TeamVote.builder().voter(지혜).team(TeamName.SNIFF).build();
        TeamVote 은비의투표 = TeamVote.builder().voter(은비).team(TeamName.READY).build();
        testRepository.저장하기(종미의투표, 윤정의투표, 지혜의투표, 은비의투표);

        // when
        List<TeamsResponse> 조회결과 = teamVoteRepository.findAllTeamsOrderByVoteCount();

        // then
        List<TeamsResponse> 예상결과 = List.of(new TeamsResponse(TeamName.SNIFF, 3L), new TeamsResponse(TeamName.READY, 1L));
        assertIterableEquals(예상결과, 조회결과);
    }
}