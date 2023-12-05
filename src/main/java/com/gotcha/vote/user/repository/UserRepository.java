package com.gotcha.vote.user.repository;

import com.gotcha.vote.polling.dto.response.CandidatesResponse;
import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByEmail(String email);
    @Query("select new com.gotcha.vote.polling.dto.response."
            + "CandidatesResponse(u, count(v)) "
            + "from User u "
            + "left join LeaderVote v "
            + "on u = v.candidate "
            + "where u.partName = :partName "
            + "group by u "
            + "order by count(v) desc")
    List<CandidatesResponse> findAllCandidateOrderByVoteCount(PartName partName);
}
