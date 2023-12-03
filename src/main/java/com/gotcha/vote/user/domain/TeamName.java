package com.gotcha.vote.user.domain;

import lombok.Getter;

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
}
