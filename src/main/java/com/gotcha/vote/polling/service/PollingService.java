package com.gotcha.vote.polling.service;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import com.gotcha.vote.global.config.user.PrincipalDetails;
import com.gotcha.vote.polling.domain.LeaderVote;
import com.gotcha.vote.polling.domain.TeamVote;
import com.gotcha.vote.polling.dto.request.LeaderVoteRequest;
import com.gotcha.vote.polling.dto.request.TeamVoteRequest;
import com.gotcha.vote.polling.dto.response.CandidatesResponse;
import com.gotcha.vote.polling.dto.response.TeamsResponse;
import com.gotcha.vote.polling.repository.LeaderVoteRepository;
import com.gotcha.vote.polling.repository.TeamVoteRepository;
import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.TeamName;
import com.gotcha.vote.user.domain.User;
import com.gotcha.vote.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PollingService {

    private final UserRepository userRepository;
    private final LeaderVoteRepository leaderVoteRepository;
    private final TeamVoteRepository teamVoteRepository;

    @Transactional
    public void voteLeader(final PrincipalDetails principal, final LeaderVoteRequest request) {
        User voter = principal.getUser();
        validateDuplicatedLeaderVote(voter);

        User candidate = userRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));
        validatePart(voter, candidate);

        LeaderVote vote = LeaderVote.builder()
                .voter(voter)
                .candidate(candidate)
                .build();
        leaderVoteRepository.save(vote);
    }

    private void validateDuplicatedLeaderVote(final User voter) {
        leaderVoteRepository.findByVoter(voter).ifPresent(user -> {
            new AppException(ErrorCode.DUPLICATED_VOTE);
        });
    }

    private void validatePart(final User voter, final User candidate) {
        if(!voter.isSamePart(candidate)) {
            throw new AppException(ErrorCode.INVALID_VOTE);
        }
    }

    public List<CandidatesResponse> findCandidates(final PartName partName) {
        return userRepository.findAllCandidateOrderByVoteCount(partName);
    }

    @Transactional
    public void voteTeam(final PrincipalDetails principal, final TeamVoteRequest request) {
        User voter = principal.getUser();
        validateDuplicatedTeamVote(voter);

        TeamVote vote = TeamVote.builder()
                .team(request.getTeamName())
                .voter(voter)
                .build();
        teamVoteRepository.save(vote);
    }

    private void validateDuplicatedTeamVote(final User voter) {
        teamVoteRepository.findByVoter(voter).ifPresent(user -> {
            new AppException(ErrorCode.DUPLICATED_VOTE);
        });
    }

    public List<TeamsResponse> findTeams() {
        List<TeamName> allTeams = Arrays.asList(TeamName.values());
        List<TeamsResponse> teamRanks = teamVoteRepository.findAllTeamsOrderByVoteCount();

        List<TeamName> rankedTeams = teamRanks.stream()
                .map(TeamsResponse::getTeam)
                .toList();
        List<TeamName> unrankedTeams = allTeams.stream()
                .filter(team -> !rankedTeams.contains(team))
                .toList();

        List<TeamsResponse> response = new ArrayList<>(teamRanks);
        response.addAll(unrankedTeams.stream()
                .map(team -> new TeamsResponse(team, 0L))
                .toList());
        return response;
    }
}
