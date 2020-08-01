package com.restapi.template.community.post.response;

import com.restapi.template.community.post.PostController;
import com.restapi.template.community.post.dto.PostDetailDto;
import com.restapi.template.common.DocsController;
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
