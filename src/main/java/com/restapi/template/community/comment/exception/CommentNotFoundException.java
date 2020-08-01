package com.restapi.template.community.comment.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("존재하지 않는 댓글입니다.");
    }

    public CommentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
