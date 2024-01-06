package com.gotcha.vote.polling.controller;

import com.gotcha.vote.global.config.user.PrincipalDetails;
import com.gotcha.vote.polling.dto.request.LeaderVoteRequest;
import com.gotcha.vote.polling.dto.request.TeamVoteRequest;
import com.gotcha.vote.polling.dto.response.CandidatesResponse;
import com.gotcha.vote.polling.dto.response.TeamsResponse;
import com.gotcha.vote.polling.service.PollingService;
import com.gotcha.vote.user.domain.PartName;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/vote")
@RequiredArgsConstructor
public class PollingController {

    private final PollingService pollingService;

    @PostMapping("/leader")
    ResponseEntity<Void> voteForPartLeader(
            @AuthenticationPrincipal final PrincipalDetails principal, @RequestBody final LeaderVoteRequest request) {
        pollingService.voteLeader(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/candidates")
    ResponseEntity<List<CandidatesResponse>> findAllCandidates(
            @Parameter(schema = @Schema(
                            allowableValues = {"FRONTEND", "BACKEND", "DESIGNER", "PRODUCT_PLANNER", "frontend", "backend", "designer", "product_planner"}))
            @RequestParam(value = "part-name") final PartName partName) {
        return ResponseEntity.ok().body(pollingService.findCandidates(partName));
    }

    @PostMapping("/demoday")
    ResponseEntity<Void> voteForDemoday(
            @AuthenticationPrincipal final PrincipalDetails principal, @RequestBody final TeamVoteRequest request) {
        pollingService.voteTeam(principal, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/teams")
    ResponseEntity<List<TeamsResponse>> findAllTeams() {
        return ResponseEntity.ok().body(pollingService.findTeams());
    }
}
