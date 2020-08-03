package com.restapi.template.security.exception;

/**
 * 유효하지 않은 RefreshToken 예외
 * 유효하지 않은 토큰입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class InvalidRefreshTokenException extends RuntimeException {
    /**
     * 유효하지 않은 토큰입니다.
     */
    public InvalidRefreshTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}
