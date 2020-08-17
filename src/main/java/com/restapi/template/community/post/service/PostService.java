package com.restapi.template.community.post.service;

import com.restapi.template.common.exception.ThisIsNotYoursException;
import com.restapi.template.community.post.Post;
import com.restapi.template.community.post.PostRepository;
import com.restapi.template.community.post.dto.PostDetailDto;
import com.restapi.template.community.post.dto.PostsDto;
import com.restapi.template.community.post.exception.PostNotFoundException;
import com.restapi.template.community.post.request.ModifyPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * 모든 게시글 조회(Paged)
     *
     * @param pageable 페이지 정보
     * @return 페이징 처리가 된 게시글
     */
    @Transactional
    public Page<PostsDto> getPosts(Pageable pageable) {
        return this.postRepository.findAllProjectedBy(pageable);
    }

    /**
     * 게시글 작성
     *
     * @param modifyPostRequest 게시글 정보
     * @return 게시글 ID
     */
    @Transactional
    public Long savePost(ModifyPostRequest modifyPostRequest) {
        Post newPost = Post.builder()
                .writerId(SecurityContextHolder.getContext().getAuthentication().getName())
                .title(modifyPostRequest.getTitle())
                .body(modifyPostRequest.getBody())
                .build();

        return this.postRepository.save(newPost).getPostId();
    }

    /**
     * 게시글 조회
     *
     * @param postId 게시글 Id
     * @return 게시글
     * @throws PostNotFoundException 존재하지 않는 게시글입니다.
     */
    @Transactional
    public PostDetailDto getPost(Long postId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
        post.increaseViews();
        return PostDetailDto.builder()
                .title(post.getTitle())
                .body(post.getBody())
                .writerId(post.getWriterId())
                .comments(post.getComments())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .views(post.getViews())
                .build();
    }

    /**
     * 게시글 수정
     *
     * @param postId            게시글 ID
     * @param modifyPostRequest 게시글 정보
     */
    @Transactional
    public void updatePost(Long postId, ModifyPostRequest modifyPostRequest) {
        permissionCheck(postId)
                .updatePost(modifyPostRequest.getTitle(), modifyPostRequest.getBody());
    }

    /**
     * 게시글 삭제
     *
     * @param postId 게시글 ID
     */
    @Transactional
    public void deletePost(Long postId) {
        this.postRepository.deleteById(permissionCheck(postId).getPostId());
    }

    /**
     * 게시글 권한 확인
     *
     * @param postId 게시글 ID
     * @return 게시글 엔터티
     * @throws PostNotFoundException   존재하지 않는 게시글입니다.
     * @throws ThisIsNotYoursException 수정권한이 없습니다.
     */
    private Post permissionCheck(Long postId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(post.getWriterId()))
            throw new ThisIsNotYoursException();
        return post;
    }
}
