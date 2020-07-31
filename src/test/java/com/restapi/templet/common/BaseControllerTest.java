package com.restapi.templet.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.templet.blog.comment.Comment;
import com.restapi.templet.blog.comment.CommentRepository;
import com.restapi.templet.blog.post.Post;
import com.restapi.templet.blog.post.PostRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "templet.restapi.com", uriPort = 443)
@Transactional
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Disabled
public class BaseControllerTest {

    @Autowired
    protected PostRepository postRepository;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Transactional
    protected Post getneratePost(int i) {
        Post post = Post.builder()
                .title("게시글" + i)
                .writerId("작성자" + i)
                .body("게시글 본문입니다.")
                .build();
        return this.postRepository.save(post);
    }

    @Transactional
    protected long addComment(Post post, int i) {
        Comment comment = Comment.builder()
                .commenterId("댓글 작성자" + i)
                .message(i + "번째 댓글")
                .build();
        Comment savedComment = this.commentRepository.save(comment);
        post.addComment(savedComment);
        this.postRepository.save(post);
        return savedComment.getCommentId();

    }
}
