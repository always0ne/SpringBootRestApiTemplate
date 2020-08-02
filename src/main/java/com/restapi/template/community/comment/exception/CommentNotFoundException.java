package com.restapi.template.community.comment.exception;

/**
 * 없는 댓글 예외
 * 존재하지 않는 댓글입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class CommentNotFoundException extends RuntimeException {
    /**
     * 존재하지 않는 댓글입니다.
     */
    public CommentNotFoundException() {
        super("존재하지 않는 댓글입니다.");
    }
}
