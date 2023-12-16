package com.gotcha.vote.polling.service;

import static com.gotcha.vote.environ.TestUser.윤정;
import static com.gotcha.vote.environ.TestUser.은비;
import static com.gotcha.vote.environ.TestUser.종미;
import static com.gotcha.vote.environ.TestUser.지혜;
import static org.assertj.core.api.Assertions.assertThat;

import com.gotcha.vote.environ.DatabaseCleaner;
import com.gotcha.vote.environ.TestRepository;
import com.gotcha.vote.polling.domain.LeaderVote;
import com.gotcha.vote.polling.domain.TeamVote;
import com.gotcha.vote.polling.dto.response.CandidatesResponse;
import com.gotcha.vote.polling.dto.response.TeamsResponse;
import com.gotcha.vote.polling.repository.LeaderVoteRepository;
import com.gotcha.vote.polling.repository.TeamVoteRepository;
import com.gotcha.vote.user.domain.TeamName;
import com.gotcha.vote.user.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Slf4j
@SpringBootTest
class PollingServiceTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamVoteRepository teamVoteRepository;
    @Autowired
    private PollingService pollingService;
    @MockBean
    private TestRepository testRepository;

    @BeforeEach
    void beforeEach() {
        databaseCleaner.clear();
    }

    @Test
    @DisplayName("팀 목록을 득표순으로 조회한다.")
    void 득표순으로_팀목록_조회하기() {
        // given
        userRepository.saveAll(List.of(종미, 윤정, 지혜, 은비));
        TeamVote 종미의투표 = TeamVote.builder().voter(종미).team(TeamName.SNIFF).build();
        TeamVote 윤정의투표 = TeamVote.builder().voter(윤정).team(TeamName.SNIFF).build();
        TeamVote 지혜의투표 = TeamVote.builder().voter(지혜).team(TeamName.SNIFF).build();
        TeamVote 은비의투표 = TeamVote.builder().voter(은비).team(TeamName.READY).build();
        teamVoteRepository.saveAll(List.of(종미의투표, 윤정의투표, 지혜의투표, 은비의투표));

        // when
        List<TeamsResponse> 조회결과 = pollingService.findTeams();

        // then
        assertThat(조회결과).hasSize(5)
                .extracting(TeamsResponse::getTeam)
                .contains(TeamName.GOTCHA, TeamName.READY, TeamName.LOCALMOOD, TeamName.SHAREMIND, TeamName.SNIFF);
    }
}