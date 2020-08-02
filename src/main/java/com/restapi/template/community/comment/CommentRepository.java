package com.restapi.template.community.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 댓글 레포지터리
 *
 * @author always0ne
 * @version 1.0
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 게시글 아이디로 조회
     *
     * @param commentId 댓글 ID
     * @return 댓글(Optional)
     */
    Optional<Comment> findByCommentId(Long commentId);
}
