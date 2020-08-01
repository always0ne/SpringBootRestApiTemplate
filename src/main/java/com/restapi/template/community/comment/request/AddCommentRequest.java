package com.restapi.template.community.comment.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentRequest {
    private String commenterId;
    private String message;
}
