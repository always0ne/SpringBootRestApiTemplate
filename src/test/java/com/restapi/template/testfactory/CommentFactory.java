package com.restapi.template.testfactory;

import com.restapi.template.api.community.comment.data.Comment;
import com.restapi.template.api.community.comment.data.CommentRepository;
import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import com.restapi.template.api.user.data.Users;
import com.restapi.template.api.user.data.UsersRepository;
import com.restapi.template.security.data.UserRole;
import com.restapi.template.security.data.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
public class CommentFactory extends AccountFactory{

    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected CommentRepository commentRepository;


    @Transactional
    public long addComment(Post post, int i) {
        Comment savedComment = this.commentRepository.save(
                new Comment(
                        generateUserAndGetUser(i),
                        i + "번째 댓글"
                )
        );
        post.addComment(savedComment);
        this.postRepository.save(post);
        return savedComment.getCommentId();
    }
}
