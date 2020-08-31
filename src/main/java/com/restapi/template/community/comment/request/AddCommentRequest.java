package com.restapi.template.community.comment.request;

import lombok.*;

/**
 * 댓글 작성 요청
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentRequest {
    /**
     * 댓글 메시지
     */
    private String message;
}
