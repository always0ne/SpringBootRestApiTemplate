package com.restapi.template.blog.post;

import com.restapi.template.blog.post.dto.PostDetailDto;
import com.restapi.template.blog.post.request.AddPostRequest;
import com.restapi.template.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Long savePost(AddPostRequest addPostRequest) {
        Post newPost = Post.builder()
                .writerId(addPostRequest.getWriterId())
                .title(addPostRequest.getTitle())
                .body(addPostRequest.getBody())
                .build();

        return this.postRepository.save(newPost).getPostId();
    }

    @Transactional
    public PostDetailDto getPost(Long postId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
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
    public void updatePost(Long postId, AddPostRequest addPostRequest) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
        post.updatePost(addPostRequest.getTitle(), addPostRequest.getBody());
    }

    @Transactional
    public void deletePost(Long postId) {
        this.postRepository.deleteById(postId);
    }
}
