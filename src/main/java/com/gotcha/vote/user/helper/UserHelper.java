package com.gotcha.vote.user.helper;

import com.gotcha.vote.exception.AppException;
import com.gotcha.vote.exception.ErrorCode;
import com.gotcha.vote.global.config.user.PrincipalDetailsService;
import com.gotcha.vote.user.domain.User;
import com.gotcha.vote.user.dto.request.UserJoinRequest;
import com.gotcha.vote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHelper {

    public final UserRepository userRepository;
    private final BCryptPasswordEncoder pwdEncoder;
    private final PrincipalDetailsService principalDetailsService;

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
        User user = request.toEntity(pwdEncoder.encode(request.getPwd()));
        userRepository.save(user);
    }

    public User findByLoginId(String loginId) {
        return userRepository
                .findByLoginId(loginId)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));
    }

    public void validatePwd(User selectedUser, String pwd) {
        if(!pwdEncoder.matches(pwd, selectedUser.getPwd())){
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public Authentication adminAuthorizationInput(User selectedUser) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(selectedUser.getLoginId());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails, "", userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
