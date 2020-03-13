package com.restapi.templet.blog.post;

import com.restapi.templet.blog.post.dto.PostDetailDto;
import com.restapi.templet.blog.post.dto.PostDto;
import com.restapi.templet.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    @Transactional
    public Page<Post> getPosts(Pageable pageable) {
        Page<Post> posts = this.postRepository.findAll(pageable);
        return posts;
    }

    @Transactional
    public Long savePost(PostDto postDto) {
        Post newPost = new Post();
        postDto.toEntity(newPost);
        newPost.setViews((long)0);
        newPost.setCreatedDate(LocalDateTime.now());
        newPost.setModifiedDate(LocalDateTime.now());
        Post post= this.postRepository.save(newPost);
        return post.getPostId();
    }

    @Transactional
    public PostDetailDto getPost(Long postId) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(()->new PostNotFoundException("존재하지 않는 게시글입니다."));
        post.setViews(post.getViews()+1);
        PostDetailDto postDetailDto = post.toDetailDto();
        this.postRepository.save(post);
        return postDetailDto;
    }

    @Transactional
    public void updatePost(Long postId, PostDto postDto) {
        Post post = this.postRepository.findByPostId(postId)
                .orElseThrow(()->new PostNotFoundException("존재하지 않는 게시글입니다."));
        postDto.toEntity(post);
        post.setModifiedDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        this.postRepository.deleteById(postId);
    }
}
