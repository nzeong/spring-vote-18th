package com.gotcha.vote.user.service;

import com.gotcha.vote.global.config.jwt.TokenProvider;
import com.gotcha.vote.user.domain.User;
import com.gotcha.vote.user.dto.request.UserJoinRequest;
import com.gotcha.vote.user.dto.request.UserLoginRequest;
import com.gotcha.vote.user.dto.response.TokenResponse;
import com.gotcha.vote.user.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserHelper userHelper;
    private final TokenProvider tokenProvider;

    public String join(UserJoinRequest request){
        String loginId = request.getLoginId();
        String email = request.getEmail();

        //유저의 loginId 중복 체크하기
        userHelper.userDuplicateCheck(loginId, email);

        //저장
        userHelper.createUser(request);

        return "SUCCESS";
    }

    public TokenResponse login(UserLoginRequest request){
        String loginId = request.getLoginId();
        String pwd = request.getPwd();

        final User selectedUser = userHelper.findByLoginId(loginId);
        final Authentication authentication = userHelper.adminAuthorizationInput(selectedUser);

        // password 맞는지 확인하기
        userHelper.validatePwd(selectedUser, pwd);

        //access 토큰 생성
        String accessToken = tokenProvider.createAccessToken(selectedUser.getId(), selectedUser.getLoginId(), authentication);

        return TokenResponse.from(accessToken);
    }
}
