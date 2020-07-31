package com.restapi.templet.blog.comment;

import com.restapi.templet.blog.comment.request.AddCommentRequest;
import com.restapi.templet.blog.comment.request.UpdateCommentRequest;
import com.restapi.templet.common.DocsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/blog/posts/{postId}", produces = MediaTypes.HAL_JSON_VALUE)
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentationModel addComment(
            @PathVariable Long postId,
            @RequestBody AddCommentRequest addCommentRequest
    ) {
        Long commentId = this.commentService.saveComment(postId, addCommentRequest);
        List<Link> links = new ArrayList<Link>();
        links.add(linkTo(CommentController.class, postId).slash(commentId).withSelfRel());
        links.add(linkTo(DocsController.class).slash("#sendComment").withRel("profile"));
        return new RepresentationModel(links);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest updateCommentRequest
    ) {
        this.commentService.updateComment(postId, commentId, updateCommentRequest);
        List<Link> links = new ArrayList<Link>();
        links.add(linkTo(CommentController.class, postId).slash(commentId).withSelfRel());
        links.add(linkTo(DocsController.class).slash("#updateComment").withRel("profile"));
        return new RepresentationModel(links);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        this.commentService.deleteComment(postId, commentId);
        List<Link> links = new ArrayList<Link>();
        links.add(linkTo(CommentController.class, postId).slash(commentId).withSelfRel());
        links.add(linkTo(DocsController.class).slash("#deleteComment").withRel("profile"));
        return new RepresentationModel(links);
    }
}
