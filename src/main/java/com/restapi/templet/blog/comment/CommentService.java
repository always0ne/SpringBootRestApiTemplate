package com.restapi.templet.blog.comment;

import com.restapi.templet.blog.post.Post;
import com.restapi.templet.blog.post.PostRepository;
import com.restapi.templet.exception.CommentNotFoundException;
import com.restapi.templet.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Long saveComment(Long postId, CommentDto commentDto) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(()->new PostNotFoundException("존재하지 않는 게시글입니다."));
        Comment comment = new Comment();
        commentDto.toEntity(comment);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setModifiedDate(LocalDateTime.now());
        Comment addedComment = this.commentRepository.save(comment);
        post.getComments().add(comment);
        this.postRepository.save(post);
        return addedComment.getCommentId();
    }

    public void updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(()->new PostNotFoundException("존재하지 않는 게시글입니다."));
        Comment comment = this.commentRepository.findByCommentId(commentId)
                .orElseThrow(()->new CommentNotFoundException("존재하지 않는 댓글입니다."));
        post.getComments().remove(comment);
        commentDto.toEntity(comment);
        comment.setModifiedDate(LocalDateTime.now());
        post.getComments().add(this.commentRepository.save(comment));
        this.postRepository.save(post);
    }

    public void deleteComment(Long postId, Long commentId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(()->new PostNotFoundException("존재하지 않는 게시글입니다."));
        Comment comment = this.commentRepository.findByCommentId(commentId)
                .orElseThrow(()->new CommentNotFoundException("존재하지 않는 댓글입니다."));
        post.getComments().remove(comment);
        this.commentRepository.delete(comment);
        this.postRepository.save(post);
    }
}
