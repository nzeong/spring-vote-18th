package com.gotcha.vote.environ;

import com.gotcha.vote.user.domain.PartName;
import com.gotcha.vote.user.domain.TeamName;
import com.gotcha.vote.user.domain.User;

public class TestUser {
    private static final String TEST_EMAIL = "test@gotcha.com";
    private static final String TEST_PASSWORD = "password";

    public static final User 종미 = User.builder().name("종미").loginId("종미").email("1"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
    public static final User 윤정 = User.builder().name("윤정").loginId("윤정").email("2"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
    public static final User 지혜 = User.builder().name("지혜").loginId("지혜").email("3"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
    public static final User 은비 = User.builder().name("은비").loginId("은비").email("4"+TEST_EMAIL).pwd(TEST_PASSWORD).partName(PartName.BACKEND).teamName(TeamName.GOTCHA).build();
}
