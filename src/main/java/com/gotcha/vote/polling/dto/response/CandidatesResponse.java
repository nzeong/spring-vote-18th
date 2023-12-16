package com.gotcha.vote.polling.dto.response;

import com.gotcha.vote.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CandidatesResponse {

    private Long id;
    private String name;
    private Long count;

    public CandidatesResponse(User user, Long count) {
        this.id = user.getId();
        this.name = user.getName();
        this.count = count;
    }
}
