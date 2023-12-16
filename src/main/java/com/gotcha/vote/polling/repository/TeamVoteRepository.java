package com.gotcha.vote.polling.repository;

import com.gotcha.vote.polling.domain.TeamVote;
import com.gotcha.vote.polling.dto.response.TeamsResponse;
import com.gotcha.vote.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamVoteRepository extends JpaRepository<TeamVote, Long> {
    Optional<TeamVote> findByVoter(User voter);
    @Query("select new com.gotcha.vote.polling.dto.response."
            + "TeamsResponse(v.team, count(v.team)) "
            + "from TeamVote v "
            + "group by v.team "
            + "order by count(v.team) desc")
    List<TeamsResponse> findAllTeamsOrderByVoteCount();
}
