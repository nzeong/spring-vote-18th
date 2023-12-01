package com.gotcha.vote.user.helper;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import com.gotcha.vote.user.domain.User;
import com.gotcha.vote.user.dto.request.UserJoinRequest;
import com.gotcha.vote.user.exception.UserIdDuplicatedException;
import com.gotcha.vote.user.exception.UserLoginIdNotFoundException;
import com.gotcha.vote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {
    public final UserRepository userRepository;

    public void userDuplicateCheck(String loginId, String email){
        userRepository.findByLoginId(loginId)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERID_DUPICATED);
                });

        userRepository.findByEmail(email)
                .ifPresent(user ->{
                    throw new AppException(ErrorCode.USEREMAIL_DUPLICATED);
                });
    }

    public void createUser(UserJoinRequest request){
        User user = request.toEntity(request.getPwd());
        userRepository.save(user);
    }


/*    public User findByLoginId(String loginId) {
        return userRepository
                .findByLoginId(loginId)
                .orElseThrow(() -> new UserLoginIdNotFoundException());
    }*/
}
