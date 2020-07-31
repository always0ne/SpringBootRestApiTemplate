package com.restapi.templet.blog.comment;

import com.restapi.templet.blog.post.Post;
import com.restapi.templet.blog.post.PostRepository;
import com.restapi.templet.exception.CommentNotFoundException;
import com.restapi.templet.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long saveComment(Long postId, CommentDto commentDto) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));

        Comment comment = this.commentRepository.save(
                Comment.builder()
                        .commenterId(commentDto.getCommenterId())
                        .message(commentDto.getMessage())
                        .build());

        return post.addComment(comment);
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
        Comment comment = this.commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new CommentNotFoundException("존재하지 않는 댓글입니다."));

        post.updateComment(comment,commentDto.getMessage());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
        Comment comment = this.commentRepository.findByCommentId(commentId)
                .orElseThrow(() -> new CommentNotFoundException("존재하지 않는 댓글입니다."));
        post.getComments().remove(comment);
        this.commentRepository.delete(comment);
    }
}
