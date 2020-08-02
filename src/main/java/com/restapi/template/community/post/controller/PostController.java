package com.restapi.template.community.post.controller;

import com.restapi.template.common.DocsController;
import com.restapi.template.community.comment.controller.CommentController;
import com.restapi.template.community.post.Post;
import com.restapi.template.community.post.dto.PostDetailDto;
import com.restapi.template.community.post.request.ModifyPostRequest;
import com.restapi.template.community.post.response.PostResponse;
import com.restapi.template.community.post.response.PostsResponse;
import com.restapi.template.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * 게시글 컨트롤러
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/blog/posts", produces = MediaTypes.HAL_JSON_VALUE)
public class PostController {

    private final PostService postService;

    /**
     * 모든 게시글 조회(Paged)
     * body랑 comments가 조회 안되게 수정필요
     *
     * @param pageable 페이지 정보
     * @param assembler 어셈블러
     * @return 페이징 처리된 게시글
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<PostsResponse> getPosts(
            Pageable pageable,
            PagedResourcesAssembler<Post> assembler
    ) {
        Page<Post> posts = this.postService.getPosts(pageable);
        PagedModel<PostsResponse> pagedResources = assembler.toModel(posts, e -> new PostsResponse(e));
        pagedResources.add(linkTo(DocsController.class).slash("getPosts").withRel("profile"));
        return pagedResources;
    }

    /**
     * 게시글 작성
     *
     * @param modifyPostRequest 게시글 정보
     * @return 게시글 ID
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel savePost(
            @RequestBody ModifyPostRequest modifyPostRequest
    ) {
        Long postId = this.postService.savePost(modifyPostRequest);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(PostController.class).slash(postId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#sendPost").withRel("profile"));
        return response;
    }

    /**
     * 게시글 조회
     *
     * @param postId 게시글 Id
     * @return 게시글
     */
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getPost(
            @PathVariable Long postId
    ) {
        PostDetailDto post = this.postService.getPost(postId);
        PostResponse postResponse = new PostResponse(post, postId);
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals(post.getWriterId())) {
            postResponse.add(linkTo(PostController.class).slash(postId).withRel("updatePost"));
            postResponse.add(linkTo(PostController.class).slash(postId).withRel("deletePost"));
        }
        postResponse.add(linkTo(CommentController.class, postId).withRel("sendComment"));
        return postResponse;
    }

    /**
     * 게시글 수정
     *
     * @param postId            게시글 Id
     * @param modifyPostRequest 게시글 정보
     * @return 수정된 게시글 데이터 링크, API Docs 링크
     */
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel updatePost(
            @PathVariable Long postId,
            @RequestBody ModifyPostRequest modifyPostRequest
    ) {
        this.postService.updatePost(postId, modifyPostRequest);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(PostController.class).slash(postId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#updatePost").withRel("profile"));
        return response;
    }

    /**
     * 게시글 삭제
     *
     * @param postId 게시글 ID
     * @return API Docs 링크
     */
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Link deletePost(
            @PathVariable Long postId
    ) {
        this.postService.deletePost(postId);
        return linkTo(DocsController.class).slash("#deletePost").withRel("profile");
    }
}
