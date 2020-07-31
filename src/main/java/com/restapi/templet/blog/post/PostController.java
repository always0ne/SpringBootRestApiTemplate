package com.restapi.templet.blog.post;

import com.restapi.templet.blog.comment.CommentController;
import com.restapi.templet.blog.post.dto.PostDetailDto;
import com.restapi.templet.blog.post.dto.PostDto;
import com.restapi.templet.blog.post.resoruce.PostListResource;
import com.restapi.templet.blog.post.resoruce.PostResource;
import com.restapi.templet.common.DocsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/blog/posts", produces = MediaTypes.HAL_JSON_VALUE)
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        Page<Post> posts = this.postService.getPosts(pageable);
        PagedModel<PostListResource> pagedResources = assembler.toModel(posts, e -> new PostListResource(e));
        pagedResources.add(linkTo(DocsController.class).slash("getPosts").withRel("profile"));
        return ResponseEntity.ok(pagedResources);
    }

    @PostMapping
    public ResponseEntity savePost(@RequestBody PostDto postDto) {
        Long postId = this.postService.savePost(postDto);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(PostController.class).slash(postId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#sendPost").withRel("profile"));
        return ResponseEntity.created(linkTo(PostController.class).slash(postId).toUri()).body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        PostDetailDto post = this.postService.getPost(postId);
        PostResource postResource = new PostResource(post, postId);
        postResource.add(linkTo(PostController.class).slash(postId).withRel("updatePost"));
        postResource.add(linkTo(PostController.class).slash(postId).withRel("deletePost"));
        postResource.add(linkTo(CommentController.class, postId).withRel("sendComment"));
        postResource.add(linkTo(DocsController.class).slash("#getPost").withRel("profile"));
        return ResponseEntity.ok(postResource);
    }

    @PutMapping("/{postId}")
    public ResponseEntity updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        this.postService.updatePost(postId, postDto);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(PostController.class).slash(postId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#updatePost").withRel("profile"));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId) {
        this.postService.deletePost(postId);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(PostController.class).slash(postId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#deletePost").withRel("profile"));
        return ResponseEntity.ok(response);
    }
}
