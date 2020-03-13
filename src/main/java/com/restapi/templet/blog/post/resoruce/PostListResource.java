package com.restapi.templet.blog.post.resoruce;

import com.restapi.templet.blog.post.Post;
import com.restapi.templet.blog.post.PostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class PostListResource extends EntityModel<Post> {

    public PostListResource(Post post, Link... links) {
        super(post, links);
        add(linkTo(PostController.class).slash(post.getPostId()).withSelfRel());
    }
}
