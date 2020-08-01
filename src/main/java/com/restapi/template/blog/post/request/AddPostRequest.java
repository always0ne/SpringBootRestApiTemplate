package com.restapi.template.blog.post.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPostRequest {
    private String title;
    private String writerId;
    private String body;
}
