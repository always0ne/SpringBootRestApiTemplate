package com.restapi.template.community.comment;

import com.restapi.template.community.post.controller.PostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
/**
 * 댓글 정보
 *
 * @author always0ne
 * @version 1.0
 */
public class CommentResource extends EntityModel<Comment> {

    public CommentResource(Comment comment, Link... links) {
        super(comment, links);
        add(linkTo(PostController.class).slash(comment.getCommentId()).withSelfRel());
        add(linkTo(PostController.class).slash(comment.getCommentId()).withRel("updateComment"));
        add(linkTo(PostController.class).slash(comment.getCommentId()).withRel("deleteComment"));
    }
}
