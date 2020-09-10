package com.restapi.template.testfactory;

import com.restapi.template.api.community.comment.data.Comment;
import com.restapi.template.api.community.comment.data.CommentRepository;
import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommentFactory {

    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;

    @Transactional
    public long addComment(Post post, int i) {
        Comment comment = Comment.builder()
                .commenterId("TestUser" + i)
                .message(i + "번째 댓글")
                .build();
        Comment savedComment = this.commentRepository.save(comment);
        post.addComment(savedComment);
        this.postRepository.save(post);
        return savedComment.getCommentId();
    }
}
