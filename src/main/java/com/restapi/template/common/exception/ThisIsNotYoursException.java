package com.restapi.template.common.exception;

public class ThisIsNotYoursException extends RuntimeException {
    public ThisIsNotYoursException() {
        super("수정 권한이 없습니다.");
    }

    public ThisIsNotYoursException(String message, Throwable cause) {
        super(message, cause);
    }
}
