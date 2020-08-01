package com.restapi.template.security.exception;

public class IdAlreadyExistsException extends RuntimeException {
    public IdAlreadyExistsException(String userId) {
        super("ID: " + userId + " 이미 사용중인 아이디입니다.");
    }

    public IdAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
