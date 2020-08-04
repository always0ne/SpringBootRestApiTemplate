package com.restapi.template.community.post;

import com.restapi.template.community.post.dto.PostDetailDto;
import com.restapi.template.community.post.dto.PostsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 게시글 레포지터리
 *
 * @author always0ne
 * @version 1.0
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * 게시글 아이디로 조회
     *
     * @param Post_id 게시글 아이디
     * @return 게시글(Optional)
     */
    Optional<Post> findByPostId(Long Post_id);

    /**
     * 모든 게시글 조회(Pagenation)
     *
     * @param pageable 페이지 정보
     * @return 모든 게시글(Page)
     */
    Page<PostsDto> findAllProjectedBy(Pageable pageable);
}
