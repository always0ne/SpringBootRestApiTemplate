package com.restapi.template.community.comment;

import com.restapi.template.common.BaseControllerTest;
import com.restapi.template.community.comment.request.AddCommentRequest;
import com.restapi.template.community.post.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("댓글 삽입 테스트")
class InsertCommentTest extends BaseControllerTest {

    @Test
    @Transactional
    @WithMockUser("TestUser1")
    @DisplayName("댓글 쓰기(성공)")
    void saveCommentSuccess() throws Exception {
        Post post = this.postFactory.generatePost(1);
        AddCommentRequest addCommentRequest = AddCommentRequest.builder()
                .message("댓글 테스트")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/blog/posts/{postId}", post.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(addCommentRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("sendComment",
                        pathParameters(
                                parameterWithName("postId").description("게시글 번호")
                        ),
                        requestFields(
                                fieldWithPath("message").description("댓글")
                        )));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}", post.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comments[0].commenterId").value("TestUser1"))
                .andExpect(jsonPath("comments[0].message").value("댓글 테스트"));
    }
}