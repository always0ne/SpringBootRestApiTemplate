package com.restapi.template.api.community.post.dto;

import com.restapi.template.api.community.comment.data.Comment;
import com.restapi.template.api.community.comment.data.CommentResource;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 상세 데이터 전송 객체
 * 내부에서 데이터 이동시 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
public class PostDetailDto {
    /**
     * 게시글 제목
     */
    private String title;
    /**
     * 작성자 ID
     */
    private String writerId;
    /**
     * 게시글 본문
     */
    private String body;
    /**
     * 조회수
     */
    private Long views;
    /**
     * 작성일
     */
    private LocalDateTime createdDate;
    /**
     * 수정일
     */
    private LocalDateTime modifiedDate;

    /**
     * 댓글들
     */
    private List<CommentResource> comments;

    @Builder
    public PostDetailDto(String title, String writerId, String body, Long views, LocalDateTime createdDate, LocalDateTime modifiedDate, List<Comment> comments) {
        this.title = title;
        this.writerId = writerId;
        this.body = body;
        this.views = views;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = new ArrayList<CommentResource>();
        if (comments != null)
            for (Comment comment : comments)
                this.comments.add(new CommentResource(comment));

    }
}
