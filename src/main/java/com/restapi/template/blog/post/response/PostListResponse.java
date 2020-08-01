package com.restapi.template.blog.post.response;

import com.restapi.template.blog.post.Post;
import com.restapi.template.blog.post.PostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PostListResponse extends EntityModel<Post> {

    public PostListResponse(Post post, Link... links) {
        super(post, links);
        add(linkTo(PostController.class).slash(post.getPostId()).withSelfRel());
    }
}
