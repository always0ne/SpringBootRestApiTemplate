package com.restapi.template.common;

import com.restapi.template.common.exception.ThisIsNotYoursException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonExceptionHandler {

    @ExceptionHandler(ThisIsNotYoursException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleNotYours(ThisIsNotYoursException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, 1002, exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleNotYours(HttpMessageNotReadableException exception) {
        String message = Objects.requireNonNull(exception.getRootCause()).getMessage().split("\\(class")[0];
        return new ErrorResponse(HttpStatus.BAD_REQUEST, 2001, message);
    }
}
