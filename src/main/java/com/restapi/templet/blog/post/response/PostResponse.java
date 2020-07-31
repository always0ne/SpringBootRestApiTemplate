package com.restapi.templet.blog.post.response;

import com.restapi.templet.blog.post.PostController;
import com.restapi.templet.blog.post.dto.PostDetailDto;
import com.restapi.templet.common.DocsController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PostResponse extends EntityModel<PostDetailDto> {

    public PostResponse(PostDetailDto post, Long postId, Link... links) {
        super(post, links);
        add(linkTo(PostController.class).slash(postId).withSelfRel());
        add(linkTo(DocsController.class).slash("#getPost").withRel("profile"));
    }
}
