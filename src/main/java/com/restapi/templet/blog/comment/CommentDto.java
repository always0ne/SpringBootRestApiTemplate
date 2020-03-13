package com.restapi.templet.blog.comment;

import com.restapi.templet.blog.post.Post;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String commenterId;
    private String message;

    public void toEntity(Comment comment){
        comment.setCommenterId(this.commenterId);
        comment.setMessage(this.message);
    }
}
