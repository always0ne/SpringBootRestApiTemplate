package com.restapi.template.community.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.template.common.Date;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "commentId", callSuper = false)
public class Comment extends Date {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 30, nullable = false)
    private String commenterId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Builder
    public Comment(String commenterId, String message) {
        super();
        this.commenterId = commenterId;
        this.message = message;
    }

    public void updateComment(String message) {
        this.message = message;
        this.update();
    }
}
