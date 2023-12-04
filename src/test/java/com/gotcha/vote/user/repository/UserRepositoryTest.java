package com.gotcha.vote.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gotcha.vote.polling.domain.LeaderVote;
import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.TeamName;
import com.gotcha.vote.user.domain.User;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    private static final String TEST_EMAIL = "test@gotcha.com";
    private static final String TEST_PASSWORD = "password";

    @Autowired
    private TestEntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("파트장 후보들을 득표 순으로 내림차순하여 정렬해 조회한다.")
    void 파트장_후보_내림차순_정렬하기() {
        // given
        User 종미 = User.builder().name("종미").loginId("종미").email("1"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
        User 윤정 = User.builder().name("윤정").loginId("윤정").email("2"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
        User 지혜 = User.builder().name("지혜").loginId("지혜").email("3"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
        User 은비 = User.builder().name("은비").loginId("은비").email("4"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
        저장하기(종미, 윤정, 지혜, 은비);

        LeaderVote 종미의투표 = LeaderVote.builder().voter(종미).candidate(윤정).build();
        LeaderVote 윤정의투표 = LeaderVote.builder().voter(윤정).candidate(지혜).build();
        LeaderVote 지혜의투표 = LeaderVote.builder().voter(지혜).candidate(윤정).build();
        LeaderVote 은비의투표 = LeaderVote.builder().voter(은비).candidate(윤정).build();
        저장하기(종미의투표, 윤정의투표, 지혜의투표, 은비의투표);

        // when
        List<User> 조회결과 = userRepository.findAllCandidateOrderByVoteCount(PartName.BACKEND);

        // then
        List<User> 예상결과 = List.of(윤정, 지혜, 종미, 은비);
        assertEquals(예상결과, 조회결과);
    }

    private void 저장하기(Object... objects) {
        for(Object o: objects) {
            em.persist(o);
        }
    }
}
