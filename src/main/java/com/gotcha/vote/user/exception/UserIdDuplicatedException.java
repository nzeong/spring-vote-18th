package com.gotcha.vote.user.exception;

public class UserIdDuplicatedException extends RuntimeException{
    public UserIdDuplicatedException(){
        super("이미 존재하는 사용자입니다.");
    }
}
