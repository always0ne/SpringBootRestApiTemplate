package com.restapi.template.community.post;

import com.restapi.template.community.post.dto.PostDetailDto;
import com.restapi.template.community.post.request.ModifyPostRequest;
import com.restapi.template.community.post.exception.PostNotFoundException;
import com.restapi.template.common.exception.ThisIsNotYoursException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Page<Post> getPosts(Pageable pageable) {
        return this.postRepository.findAll(pageable);
    }

    @Transactional
    public Long savePost(ModifyPostRequest modifyPostRequest) {
        Post newPost = Post.builder()
                .writerId(SecurityContextHolder.getContext().getAuthentication().getName())
                .title(modifyPostRequest.getTitle())
                .body(modifyPostRequest.getBody())
                .build();

        return this.postRepository.save(newPost).getPostId();
    }

    @Transactional
    public PostDetailDto getPost(Long postId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException());
        post.increaseViews();
        return PostDetailDto.builder()
                .title(post.getTitle())
                .body(post.getBody())
                .wirterId(post.getWriterId())
                .comments(post.getComments())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .views(post.getViews())
                .build();
    }

    @Transactional
    public void updatePost(Long postId, ModifyPostRequest modifyPostRequest) {
        permissionCheck(postId)
                .updatePost(modifyPostRequest.getTitle(), modifyPostRequest.getBody());
    }

    @Transactional
    public void deletePost(Long postId) {
        this.postRepository.deleteById(permissionCheck(postId).getPostId());
    }

    private Post permissionCheck(Long postId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(PostNotFoundException::new);
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(post.getWriterId()))
            throw new ThisIsNotYoursException();
        return post;
    }
}
