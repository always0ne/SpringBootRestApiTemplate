package com.restapi.templet.blog.comment;

import com.restapi.templet.blog.post.PostController;
import com.restapi.templet.blog.post.PostService;
import com.restapi.templet.common.DocsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/blog/posts/{postId}", produces = MediaTypes.HAL_JSON_VALUE)
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping
    public ResponseEntity saveComment(@PathVariable Long postId, @RequestBody CommentDto commentDto){
        Long commentId = this.commentService.saveComment(postId,commentDto);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(CommentController.class,postId).slash(commentId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#sendComment").withRel("profile"));
        return ResponseEntity.created(linkTo(CommentController.class,postId).slash(commentId).toUri()).body(response);
    }
    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long postId , @PathVariable Long commentId, @RequestBody CommentDto commentDto){
        this.commentService.updateComment(postId,commentId,commentDto);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(CommentController.class,postId).slash(commentId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#updateComment").withRel("profile"));
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long postId , @PathVariable Long commentId){
        this.commentService.deleteComment(postId,commentId);
        RepresentationModel response = new RepresentationModel();
        response.add(linkTo(CommentController.class,postId).slash(commentId).withSelfRel());
        response.add(linkTo(DocsController.class).slash("#deleteComment").withRel("profile"));
        return ResponseEntity.ok(response);
    }
}
