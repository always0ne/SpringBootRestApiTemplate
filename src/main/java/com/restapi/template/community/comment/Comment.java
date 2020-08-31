package com.restapi.template.community.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.template.common.Date;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 댓글 엔터티
 *
 * @author always0ne
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "commentId", callSuper = false)
public class Comment extends Date {
    /**
     * pk
     */
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    /**
     * 댓글 작성자
     */
    @Column(length = 30, nullable = false)
    private String commenterId;
    /**
     * 댓글
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Builder
    public Comment(String commenterId, String message) {
        super();
        this.commenterId = commenterId;
        this.message = message;
    }

    /**
     * 댓글 수정
     *
     * @param message 수정할 메시지
     */
    public void updateComment(String message) {
        this.message = message;
        this.updateModifyDate();
    }
}
