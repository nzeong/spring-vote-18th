package com.gotcha.vote.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PartName partName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamName teamName;

    @Builder
    public User(String loginId, String email, String pwd, String name, PartName partName, TeamName teamName) {
        this.loginId = loginId;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.partName = partName;
        this.teamName = teamName;
    }

    public boolean isSamePart(User other) {
        return this.partName.equals(other.getPartName());
    }

    @Override
    public boolean equals(Object o) {
        if(((User)o).getLoginId().equals(loginId)) {
            return true;
        }
        return false;
    }
}
