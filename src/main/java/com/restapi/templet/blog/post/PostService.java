package com.restapi.templet.blog.post;

import com.restapi.templet.blog.post.dto.PostDetailDto;
import com.restapi.templet.blog.post.dto.PostDto;
import com.restapi.templet.exception.PostNotFoundException;
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
    public Long savePost(PostDto postDto) {
        Post newPost = Post.builder()
                .writerId(postDto.getWriterId())
                .title(postDto.getTitle())
                .body(postDto.getBody())
                .build();

        return this.postRepository.save(newPost).getPostId();
    }

    @Transactional
    public PostDetailDto getPost(Long postId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
        return post.toDetailDto();
    }

    @Transactional
    public void updatePost(Long postId, PostDto postDto) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
        post.updatePost(postDto.getTitle(), postDto.getBody());
    }

    @Transactional
    public void deletePost(Long postId) {
        this.postRepository.deleteById(postId);
    }
}
