package com.restapi.template.security.exception;

/**
 * 토큰 기간 만료 예외
 * 만료된 토큰입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class TokenExpiredException extends RuntimeException {
    /**
     *만료된 토큰입니다.
     */
    public TokenExpiredException() {
        super("만료된 토큰입니다.");
    }
}
