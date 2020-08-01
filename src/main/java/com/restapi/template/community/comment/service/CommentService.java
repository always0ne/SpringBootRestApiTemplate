package com.restapi.template.community.comment.service;

import com.restapi.template.community.comment.Comment;
import com.restapi.template.community.comment.CommentRepository;
import com.restapi.template.community.comment.request.AddCommentRequest;
import com.restapi.template.community.comment.request.UpdateCommentRequest;
import com.restapi.template.community.post.Post;
import com.restapi.template.community.post.PostRepository;
import com.restapi.template.community.comment.exception.CommentNotFoundException;
import com.restapi.template.community.post.exception.PostNotFoundException;
import com.restapi.template.common.exception.ThisIsNotYoursException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

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

    @Transactional
    public void updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);

        post.updateComment(
                getMyComment(commentId),
                updateCommentRequest.getMessage());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
        Comment comment = getMyComment(commentId);

        post.getComments().remove(comment);
        this.commentRepository.delete(comment);
    }

    public Comment getMyComment(Long commentId) {
        Comment comment = this.commentRepository.findByCommentId(commentId)
                .orElseThrow(CommentNotFoundException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(comment.getCommenterId()))
            throw new ThisIsNotYoursException();
        return comment;
    }

}
