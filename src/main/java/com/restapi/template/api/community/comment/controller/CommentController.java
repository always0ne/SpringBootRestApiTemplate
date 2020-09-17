package com.restapi.template.api.community.comment.controller;

import com.restapi.template.api.common.DocsController;
import com.restapi.template.api.common.response.LinksResponse;
import com.restapi.template.api.community.comment.request.AddCommentRequest;
import com.restapi.template.api.community.comment.request.UpdateCommentRequest;
import com.restapi.template.api.community.comment.service.CommentService;
import com.restapi.template.api.community.post.controller.PostController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping(value = "/board/posts/{postId}", produces = MediaTypes.HAL_JSON_VALUE)
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
    public LinksResponse addComment(
            @PathVariable Long postId,
            @RequestBody AddCommentRequest addCommentRequest
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        this.commentService.saveComment(postId, requestUserId, addCommentRequest);

        return new LinksResponse(
                linkTo(PostController.class).slash(postId).withSelfRel(),
                linkTo(DocsController.class).slash("#sendComment").withRel("profile")
        );
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
    public LinksResponse updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest updateCommentRequest
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        this.commentService.updateComment(postId, commentId, requestUserId, updateCommentRequest);

        return new LinksResponse(
                linkTo(PostController.class).slash(postId).withSelfRel(),
                linkTo(DocsController.class).slash("#updateComment").withRel("profile")
        );
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
    public LinksResponse deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        this.commentService.deleteComment(postId, commentId, requestUserId);

        return new LinksResponse(
                linkTo(PostController.class).slash(postId).withSelfRel(),
                linkTo(DocsController.class).slash("#deleteComment").withRel("profile")
        );
    }
}
