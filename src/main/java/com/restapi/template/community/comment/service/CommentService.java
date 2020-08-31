package com.restapi.template.community.comment.service;

import com.restapi.template.common.exception.ThisIsNotYoursException;
import com.restapi.template.community.comment.Comment;
import com.restapi.template.community.comment.CommentRepository;
import com.restapi.template.community.comment.exception.CommentNotFoundException;
import com.restapi.template.community.comment.request.AddCommentRequest;
import com.restapi.template.community.comment.request.UpdateCommentRequest;
import com.restapi.template.community.post.Post;
import com.restapi.template.community.post.PostRepository;
import com.restapi.template.community.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 입력
     *
     * @param postId            게시글 ID
     * @param addCommentRequest 댓글 작성 요청
     * @return 댓글 ID
     * @throws PostNotFoundException 존재하지 않는 게시글입니다.
     */
    @Transactional
    public Long saveComment(Long postId, AddCommentRequest addCommentRequest) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);

        Comment comment = this.commentRepository.save(
                Comment.builder()
                        .commenterId(SecurityContextHolder.getContext().getAuthentication().getName())
                        .message(addCommentRequest.getMessage())
                        .build());

        return post.addComment(comment);
    }

    /**
     * 댓글 수정
     *
     * @param postId               게시글 ID
     * @param commentId            댓글 ID
     * @param updateCommentRequest 댓글 수정 요청
     * @throws PostNotFoundException    존재하지 않는 게시글입니다.
     * @throws CommentNotFoundException 존재하지 않는 댓글입니다.
     */
    @Transactional
    public void updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);

        post.updateComment(
                getMyComment(commentId),
                updateCommentRequest.getMessage());
    }

    /**
     * 댓글 삭제
     *
     * @param postId    게시글 ID
     * @param commentId 댓글 ID
     * @throws PostNotFoundException    존재하지 않는 게시글입니다.
     * @throws CommentNotFoundException 존재하지 않는 댓글입니다.
     */
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
        Comment comment = getMyComment(commentId);
        post.deleteComment(comment);
        this.commentRepository.delete(comment);
    }

    /**
     * 내 댓글 가져오기
     *
     * @param commentId 댓글 ID
     * @return 댓글 엔터티
     * @throws CommentNotFoundException 존재하지 않는 댓글입니다.
     */
    public Comment getMyComment(Long commentId) {
        Comment comment = this.commentRepository.findByCommentId(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(comment.getCommenterId()))
            throw new ThisIsNotYoursException();
        return comment;
    }

}
