package com.restapi.template.account.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("ID: " + userId + " 회원가입이 되어있지 않거나 잠긴 계정입니다.");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
