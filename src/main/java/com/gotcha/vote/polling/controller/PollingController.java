package com.gotcha.vote.polling.controller;

import com.gotcha.vote.global.config.user.PrincipalDetails;
import com.gotcha.vote.polling.service.PollingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
}
