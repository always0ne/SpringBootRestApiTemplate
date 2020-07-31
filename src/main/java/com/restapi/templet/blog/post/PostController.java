package com.restapi.templet.blog.post;

import com.restapi.templet.blog.comment.CommentController;
import com.restapi.templet.blog.post.request.AddPostRequest;
import com.restapi.templet.blog.post.response.PostListResponse;
import com.restapi.templet.blog.post.response.PostResponse;
import com.restapi.templet.common.DocsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/blog/posts", produces = MediaTypes.HAL_JSON_VALUE)
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<PostListResponse> getPosts(
            Pageable pageable,
            PagedResourcesAssembler<Post> assembler
    ) {
        Page<Post> posts = this.postService.getPosts(pageable);
        PagedModel<PostListResponse> pagedResources = assembler.toModel(posts, e -> new PostListResponse(e));
        pagedResources.add(linkTo(DocsController.class).slash("getPosts").withRel("profile"));
        return pagedResources;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel savePost(
            @RequestBody AddPostRequest addPostRequest
    ) {
        Long postId = this.postService.savePost(addPostRequest);
        List<Link> links = new ArrayList<Link>();
        links.add(linkTo(PostController.class).slash(postId).withSelfRel());
        links.add(linkTo(DocsController.class).slash("#sendPost").withRel("profile"));
        return new RepresentationModel(links);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse getPost(
            @PathVariable Long postId
    ) {
        PostResponse postResponse = new PostResponse(this.postService.getPost(postId), postId);
        postResponse.add(linkTo(PostController.class).slash(postId).withRel("updatePost"));
        postResponse.add(linkTo(PostController.class).slash(postId).withRel("deletePost"));
        postResponse.add(linkTo(CommentController.class, postId).withRel("sendComment"));
        return postResponse;
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel updatePost(
            @PathVariable Long postId,
            @RequestBody AddPostRequest addPostRequest
    ) {
        this.postService.updatePost(postId, addPostRequest);
        List<Link> links = new ArrayList<Link>();
        links.add(linkTo(PostController.class).slash(postId).withSelfRel());
        links.add(linkTo(DocsController.class).slash("#updatePost").withRel("profile"));
        return new RepresentationModel(links);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel deletePost(
            @PathVariable Long postId
    ) {
        this.postService.deletePost(postId);
        List<Link> links = new ArrayList<Link>();
        links.add(linkTo(PostController.class).slash(postId).withSelfRel());
        links.add(linkTo(DocsController.class).slash("#deletePost").withRel("profile"));
        return new RepresentationModel(links);
    }
}
