package com.restapi.template.community.comment.controller;

import com.restapi.template.common.response.ErrorResponse;
import com.restapi.template.community.comment.exception.CommentNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 댓글 서비스에서 발생하는 Exception Handler
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentExceptionHandler {
    /**
     * 없는 댓글 예외 발생
     *
     * @param exception 없는 댓글 예외
     * @return NOT_FOUND
     */
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleCommentNotFound(CommentNotFoundException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "1201", exception.getMessage());
    }
}
