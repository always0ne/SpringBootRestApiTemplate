package com.restapi.templet.blog.post.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private String title;
    private String writerId;
    private String body;
}
