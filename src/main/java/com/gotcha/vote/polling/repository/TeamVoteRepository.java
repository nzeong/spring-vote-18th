package com.gotcha.vote.polling.repository;

import com.gotcha.vote.polling.domain.TeamVote;
import com.gotcha.vote.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamVoteRepository extends JpaRepository<TeamVote, Long> {
    Optional<TeamVote> findByVoter(User voter);
}
