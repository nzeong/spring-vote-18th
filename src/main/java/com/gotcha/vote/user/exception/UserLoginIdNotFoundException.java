package com.gotcha.vote.user.exception;

public class UserLoginIdNotFoundException extends RuntimeException{
    public UserLoginIdNotFoundException(){
        super("존재하지 않는 아이디입니다.");
    }
}
