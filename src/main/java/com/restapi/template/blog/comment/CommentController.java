package com.restapi.template.blog.comment;

import com.restapi.template.blog.comment.request.AddCommentRequest;
import com.restapi.template.blog.comment.request.UpdateCommentRequest;
import com.restapi.template.common.DocsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(CommentController.class, postId).slash(commentId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#sendComment").withRel("profile"));
        return response;
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest updateCommentRequest
    ) {
        this.commentService.updateComment(postId, commentId, updateCommentRequest);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(CommentController.class, postId).slash(commentId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#updateComment").withRel("profile"));
        return response;
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public RepresentationModel deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        this.commentService.deleteComment(postId, commentId);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(CommentController.class, postId).slash(commentId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#deleteComment").withRel("profile"));
        return response;
    }
}
