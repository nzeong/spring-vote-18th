package com.gotcha.vote.user.repository;

import static com.gotcha.vote.environ.TestUser.윤정;
import static com.gotcha.vote.environ.TestUser.은비;
import static com.gotcha.vote.environ.TestUser.종미;
import static com.gotcha.vote.environ.TestUser.지혜;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gotcha.vote.environ.TestRepository;
import com.gotcha.vote.polling.domain.LeaderVote;
import com.gotcha.vote.polling.dto.response.CandidatesResponse;
import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.User;
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
public class UserRepositoryTest {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("파트장 후보들을 득표 순으로 내림차순하여 정렬해 조회한다.")
    void 파트장_후보_내림차순_정렬하기() {
        // given
        testRepository.저장하기(종미, 윤정, 지혜, 은비);

        LeaderVote 종미의투표 = LeaderVote.builder().voter(종미).candidate(윤정).build();
        LeaderVote 윤정의투표 = LeaderVote.builder().voter(윤정).candidate(지혜).build();
        LeaderVote 지혜의투표 = LeaderVote.builder().voter(지혜).candidate(윤정).build();
        LeaderVote 은비의투표 = LeaderVote.builder().voter(은비).candidate(윤정).build();
        testRepository.저장하기(종미의투표, 윤정의투표, 지혜의투표, 은비의투표);

        // when
        List<CandidatesResponse> 조회결과 = userRepository.findAllCandidateOrderByVoteCount(PartName.BACKEND);

        // then
        List<String> 예상결과 = List.of(윤정.getName(), 지혜.getName(), 종미.getName(), 은비.getName());
        List<Long> 득표 = List.of(3L, 1L, 0L, 0L);
        assertEquals(예상결과, 조회결과.stream().map(CandidatesResponse::getName).toList());
        assertEquals(득표, 조회결과.stream().map(CandidatesResponse::getCount).toList());
    }
}
