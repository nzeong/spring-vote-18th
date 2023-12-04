package com.gotcha.vote.polling.controller;

import com.gotcha.vote.global.config.user.PrincipalDetails;
import com.gotcha.vote.polling.dto.response.CandidatesResponse;
import com.gotcha.vote.polling.service.PollingService;
import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.TeamName;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/vote")
@RequiredArgsConstructor
public class PollingController {

    private final PollingService pollingService;

    @PostMapping("/leader")
    ResponseEntity<Void> voteForPartLeader(
            @AuthenticationPrincipal final PrincipalDetails principal, @RequestParam final Long candidateId) {
        pollingService.voteLeader(principal, candidateId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/candidates")
    ResponseEntity<List<CandidatesResponse>> findAllCandidates(@RequestParam final PartName partName) {
        return ResponseEntity.ok().body(pollingService.findCandidates(partName));
    }

    @PostMapping("/demoday")
    ResponseEntity<Void> voteForDemoday(
            @AuthenticationPrincipal final PrincipalDetails principal, @RequestParam final TeamName teamName) {
        pollingService.voteTeam(principal, teamName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
