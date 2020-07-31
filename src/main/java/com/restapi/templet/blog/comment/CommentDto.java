package com.restapi.templet.blog.comment;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String commenterId;
    private String message;
}
