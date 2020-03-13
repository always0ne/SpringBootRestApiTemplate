package com.restapi.templet.blog.post.dto;

import com.restapi.templet.blog.post.Post;
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

    public void toEntity(Post post){
        post.setTitle(this.title);
        post.setWriterId(this.writerId);
        post.setBody(this.body);
    }
}
