package com.restapi.template.community.comment.controller;

import com.restapi.template.common.DocsController;
import com.restapi.template.community.comment.request.AddCommentRequest;
import com.restapi.template.community.comment.request.UpdateCommentRequest;
import com.restapi.template.community.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * 댓글 컨트롤러
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/blog/posts/{postId}", produces = MediaTypes.HAL_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * @param postId            게시글 ID
     * @param addCommentRequest 댓글 정보
     * @return API Docs 링크
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Link addComment(
            @PathVariable Long postId,
            @RequestBody AddCommentRequest addCommentRequest
    ) {
        this.commentService.saveComment(postId, addCommentRequest);

        return linkTo(DocsController.class).slash("#sendComment").withRel("profile");
    }

    /**
     * 댓글 수정
     *
     * @param postId               게시글 ID
     * @param commentId            게시글 ID
     * @param updateCommentRequest 댓글 정보
     * @return API Docs 링크
     */
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Link updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest updateCommentRequest
    ) {
        this.commentService.updateComment(postId, commentId, updateCommentRequest);

        return linkTo(DocsController.class).slash("#updateComment").withRel("profile");
    }

    /**
     * 댓글 삭제
     *
     * @param postId    게시글 ID
     * @param commentId 게시글 ID
     * @return API Docs 링크
     */
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public Link deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        this.commentService.deleteComment(postId, commentId);

        return linkTo(DocsController.class).slash("#deleteComment").withRel("profile");
    }
}
