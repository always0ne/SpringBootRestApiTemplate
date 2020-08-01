package com.restapi.template.community.comment;

import com.restapi.template.common.BaseControllerTest;
import com.restapi.template.community.post.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("댓글 삭제 테스트")
class DeleteCommentTest extends BaseControllerTest {

    @Test
    @Transactional
    @WithMockUser("TestUser1")
    @DisplayName("댓글 지우기")
    void deleteComment() throws Exception {

        Post post = this.postFactory.generatePost(1);
        long commentId = this.commentFactory.addComment(post, 1);
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/blog/posts/{postId}/{commentId}", post.getPostId(), commentId))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("deleteComment",
                        pathParameters(
                                parameterWithName("postId").description("게시글 번호"),
                                parameterWithName("commentId").description("댓글 번호")
                        )));
    }
}