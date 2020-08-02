package com.restapi.template.community.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.template.common.Date;
import com.restapi.template.community.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 엔터티
 *
 * @author always0ne
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@Getter
public class Post extends Date {
    /**
     * pk
     */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    /**
     * 작성자 ID
     */
    @Column(length = 30, nullable = false)
    private String writerId;
    /**
     * 글 제목
     */
    @Column(length = 100, nullable = false)
    private String title;
    /**
     * 본문
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;
    /**
     * 조회수
     */
    @Column(nullable = false)
    private Long views;
    /**
     * 댓글들
     *
     * @see com.restapi.template.community.comment.Comment
     */
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

    /**
     * 게시글 수정
     * 데이터 변경 후 수정일 갱신
     *
     * @param title 글 제목
     * @param body  글 본문
     */
    public void updatePost(String title, String body) {
        this.title = title;
        this.body = body;
        this.update();
    }

    /**
     * 댓글 추가
     *
     * @param comment 댓글
     * @return 댓글ID
     */
    public Long addComment(Comment comment) {
        if (this.comments == null)
            this.comments = new ArrayList<Comment>();
        this.comments.add(comment);
        return comment.getCommentId();
    }

    /**
     * 댓글 수정
     *
     * @param comment 댓글
     * @param message 수정할 메시지
     */
    public void updateComment(Comment comment, String message) {
        comment.updateComment(message);
        this.comments.set(this.comments.indexOf(comment), comment);
    }

    /**
     * 조회수 증가
     */
    public void increaseViews() {
        this.views++;
    }
}
