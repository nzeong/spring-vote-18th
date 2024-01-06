package com.gotcha.vote.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public enum TeamName {
    SHAREMIND("셰어마인드"),
    LOCALMOOD("로컬무드"),
    READY("레디"),
    SNIFF("스니프"),
    GOTCHA("갓챠");

    private final String value;

    TeamName(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TeamName from(String s) {
        return TeamName.valueOf(s.toUpperCase());
    }
}
