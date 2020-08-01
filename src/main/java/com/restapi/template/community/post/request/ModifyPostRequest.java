package com.restapi.template.community.post.request;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyPostRequest {
    private String title;
    private String body;
}
