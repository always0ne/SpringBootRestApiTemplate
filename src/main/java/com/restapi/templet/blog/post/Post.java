package com.restapi.templet.blog.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.templet.blog.comment.Comment;
import com.restapi.templet.blog.post.dto.PostDetailDto;
import com.restapi.templet.common.Date;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post extends Date {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 30, nullable =false)
    private String writerId;

    @Column(length = 100, nullable =false)
    private String title;

    @JsonIgnore
    @Column(columnDefinition = "TEXT",nullable = false)
    private String body;

    @Column(nullable = false)
    private Long views;

    @JsonIgnore
    @OneToMany
    @Builder.Default
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<Comment>();

    public PostDetailDto toDetailDto(){
        PostDetailDto postDetailDto = PostDetailDto.builder()
                .title(this.title)
                .body(this.body)
                .wirterId(this.writerId)
                .comments(this.comments)
                .createdDate(this.getCreatedDate())
                .modifiedDate(this.getModifiedDate())
                .views(this.views)
                .build();
        return postDetailDto;
    }
}
