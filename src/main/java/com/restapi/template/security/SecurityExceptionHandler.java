package com.restapi.template.security;

import com.restapi.template.common.ErrorResponse;
import com.restapi.template.common.exception.ThisIsNotYoursException;
import com.restapi.template.security.exception.IdAlreadyExistsException;
import com.restapi.template.security.exception.UserNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    @ExceptionHandler(IdAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ErrorResponse handleIdExists(IdAlreadyExistsException exception) {
        return new ErrorResponse(HttpStatus.ACCEPTED, 0001, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleUserNotFound(UserNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, 0002, exception.getMessage());
    }
}
