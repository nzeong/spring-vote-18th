package com.gotcha.vote.polling.repository;

import com.gotcha.vote.polling.domain.LeaderCandidate;
import com.gotcha.vote.polling.domain.LeaderVote;
import com.gotcha.vote.polling.dto.response.CandidatesResponse;
import com.gotcha.vote.user.domain.PartName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeaderCandidateRepository extends JpaRepository<LeaderCandidate, Long> {
    @Query("SELECT new com.gotcha.vote.polling.dto.response.CandidatesResponse(u, COUNT(v)) " +
            "FROM LeaderCandidate u " +
            "LEFT JOIN LeaderVote v ON u = v.candidate " +
            "WHERE u.partName = :partName " +
            "GROUP BY u.id, u.name, u.partName " +
            "ORDER BY COUNT(v) DESC")
    List<CandidatesResponse> findAllCandidateOrderByVoteCount(@Param("partName") PartName partName);
}
