package com.gotcha.vote.user.dto.request;

import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.TeamName;
import com.gotcha.vote.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinRequest {
    private String loginId;
    private String email;
    private String pwd;
    private String name;
    private PartName partName;
    private TeamName teamName;

    @Builder
    public UserJoinRequest(String loginId, String email, String pwd, String name, PartName partName, TeamName teamName) {
        this.loginId = loginId;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.partName = partName;
        this.teamName = teamName;
    }

    public User toEntity(String encodedPwd) {
        return User.builder()
                .loginId(loginId)
                .email(email)
                .pwd(encodedPwd)
                .name(name)
                .partName(partName) // enum 설정
                .teamName(teamName)
                .build();
    }
}
