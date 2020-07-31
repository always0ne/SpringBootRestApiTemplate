package com.restapi.templet.blog.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.templet.blog.comment.Comment;
import com.restapi.templet.common.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post extends Date {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 30, nullable = false)
    private String writerId;

    @Column(length = 100, nullable = false)
    private String title;

    @JsonIgnore
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    @Column(nullable = false)
    private Long views;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    @Builder
    public Post(String writerId, String title, String body) {
        super();
        this.writerId = writerId;
        this.title = title;
        this.body = body;
        this.views = (long) 0;
    }

    public void updatePost(String title, String body) {
        this.title = title;
        this.body = body;
        this.update();
    }

    public Long addComment(Comment comment) {
        if (this.comments == null)
            this.comments = new ArrayList<Comment>();
        this.comments.add(comment);
        return comment.getCommentId();
    }

    public void updateComment(Comment comment, String message) {
        comment.updateComment(message);
        this.comments.set(this.comments.indexOf(comment), comment);
    }

    public void increaseViews() {
        this.views++;
    }
}
