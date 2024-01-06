package com.gotcha.vote.user;

import com.gotcha.vote.user.dto.request.UserJoinRequest;
import com.gotcha.vote.user.dto.request.UserLoginRequest;
import com.gotcha.vote.user.dto.response.TokenResponse;
import com.gotcha.vote.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto){
        userService.join(dto);
        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest request){

        //로그인 시 TokenResponse return
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(request));
    }
}
