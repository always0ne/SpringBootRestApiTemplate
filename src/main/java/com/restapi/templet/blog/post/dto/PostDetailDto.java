package com.restapi.templet.blog.post.dto;

import com.restapi.templet.blog.comment.Comment;
import com.restapi.templet.blog.comment.CommentResource;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDetailDto {
    private String title;
    private String writerId;
    private String body;
    private Long views;
    private LocalDateTime  createdDate;
    private LocalDateTime modifiedDate;
    private List<CommentResource> comments = new ArrayList<CommentResource>();

    @Builder
    public PostDetailDto(String title, String wirterId, String body, Long views, LocalDateTime createdDate, LocalDateTime modifiedDate, List<Comment> comments){
        this.title = title;
        this.writerId = wirterId;
        this.body = body;
        this.views = views;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        if(comments.isEmpty())
            this.comments = null;
        else{
            List<CommentResource> commentList = new ArrayList<CommentResource>();
            for(Comment comment : comments){
                CommentResource commentResource = new CommentResource(comment);
                commentList.add(commentResource);
            }
            this.comments = commentList;
        }

    }
}
