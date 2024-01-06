package com.gotcha.vote.polling.repository;

import com.gotcha.vote.polling.domain.LeaderVote;
import com.gotcha.vote.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaderVoteRepository extends JpaRepository<LeaderVote, Long> {
    Optional<LeaderVote> findByVoter(User voter);
}
