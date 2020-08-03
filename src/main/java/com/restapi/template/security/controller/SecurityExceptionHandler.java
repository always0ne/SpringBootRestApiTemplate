package com.restapi.template.security.controller;

import com.restapi.template.common.response.ErrorResponse;
import com.restapi.template.security.exception.CantSignInException;
import com.restapi.template.security.exception.IdAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 회원 인증상에서 발생하는 Exception Handler
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {
    /**
     * 아이디 중복 예외 발생
     *
     * @param exception 아이디 중복 예외
     * @return ACCEPTED
     */
    @ExceptionHandler(IdAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ErrorResponse handleIdExists(IdAlreadyExistsException exception) {
        return new ErrorResponse(HttpStatus.ACCEPTED, 0001, exception.getMessage());
    }

    /**
     * 회원 인증 예외 발생
     *
     * @param exception 인증 불가 예외
     * @return FORBIDDEN
     */
    @ExceptionHandler(CantSignInException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleUserNotFound(CantSignInException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, 0002, exception.getMessage());
    }
}
