package com.gotcha.vote.user.domain;

import lombok.Getter;

@Getter
public enum PartName {
    FRONTEND("프론트엔드"),
    BACKEND("백엔드"),
    DESIGNER("디자이너"),
    PRODUCT_PLANNER("기획자");

    private final String value;

    PartName(String value) {
        this.value = value;
    }
}