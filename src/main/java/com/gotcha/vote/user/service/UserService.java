package com.gotcha.vote.user.service;

import com.gotcha.vote.user.dto.request.UserJoinRequest;
import com.gotcha.vote.user.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHelper userHelper;

    public String join(UserJoinRequest request){
        String loginId = request.getLoginId();
        String email = request.getEmail();

        //유저의 loginId 중복 체크하기
        userHelper.userDuplicateCheck(loginId, email);

        //저장
        userHelper.createUser(request);

        return "SUCCESS";
    }
}
