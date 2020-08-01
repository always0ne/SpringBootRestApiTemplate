package com.restapi.template.community.post.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("존재하지 않는 게시글입니다.");
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
