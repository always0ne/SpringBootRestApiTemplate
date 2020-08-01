package com.restapi.template.community.comment.controller;

import com.restapi.template.community.comment.exception.CommentNotFoundException;
import com.restapi.template.common.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleCommentNotFound(CommentNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND,1201 ,exception.getMessage());
    }
}
