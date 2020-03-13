package com.restapi.templet.blog.post.resoruce;

import com.restapi.templet.blog.post.PostController;
import com.restapi.templet.blog.post.dto.PostDetailDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PostResource extends EntityModel<PostDetailDto> {

    public PostResource(PostDetailDto post, Long postId, Link... links) {
        super(post, links);
        add(linkTo(PostController.class).slash(postId).withSelfRel());
    }
}
