package com.gotcha.vote.polling.service;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import com.gotcha.vote.global.config.user.PrincipalDetails;
import com.gotcha.vote.polling.domain.LeaderVote;
import com.gotcha.vote.polling.repository.LeaderVoteRepository;
import com.gotcha.vote.user.domain.User;
import com.gotcha.vote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PollingService {

    private final UserRepository userRepository;
    private final LeaderVoteRepository leaderVoteRepository;

    @Transactional
    public void voteLeader(final PrincipalDetails principal, final Long candidateId) {
        User voter = principal.getUser();
        validateDuplicatedVote(voter);

        User candidate = userRepository.findById(candidateId)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));
        validatePart(voter, candidate);

        LeaderVote vote = LeaderVote.builder()
                .voter(voter)
                .candidate(candidate)
                .build();
        leaderVoteRepository.save(vote);
    }

    private void validateDuplicatedVote(final User voter) {
        leaderVoteRepository.findByVoter(voter).ifPresent(user -> {
            new AppException(ErrorCode.DUPLICATED_VOTE);
        });
    }

    private void validatePart(final User voter, final User candidate) {
        if(!voter.isSamePart(candidate)) {
            throw new AppException(ErrorCode.INVALID_VOTE);
        }
    }
}
