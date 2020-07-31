package com.restapi.templet.blog.post.response;

import com.restapi.templet.blog.post.Post;
import com.restapi.templet.blog.post.PostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PostListResponse extends EntityModel<Post> {

    public PostListResponse(Post post, Link... links) {
        super(post, links);
        add(linkTo(PostController.class).slash(post.getPostId()).withSelfRel());
    }
}
