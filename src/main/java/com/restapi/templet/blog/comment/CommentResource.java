package com.restapi.templet.blog.comment;

import com.restapi.templet.blog.post.PostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CommentResource extends EntityModel<Comment> {

    public CommentResource(Comment comment, Link... links) {
        super(comment, links);
        add(linkTo(PostController.class).slash(comment.getCommentId()).withSelfRel());
        add(linkTo(PostController.class).slash(comment.getCommentId()).withRel("updateComment"));
        add(linkTo(PostController.class).slash(comment.getCommentId()).withRel("deleteComment"));
    }
}
