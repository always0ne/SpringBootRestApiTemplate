package com.restapi.templet.blog.comment.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class addCommentRequest {
    private String commenterId;
    private String message;
}
