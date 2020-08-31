package com.restapi.template.community.comment;

import com.restapi.template.common.BaseControllerTest;
import com.restapi.template.community.comment.request.UpdateCommentRequest;
import com.restapi.template.community.post.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("댓글 수정 테스트")
class UpdateCommentTest extends BaseControllerTest {

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("댓글 수정하기(성공)")
    void updateCommentSuccess() throws Exception {
        Post post = this.postFactory.generatePost(1);
        long commentId = this.commentFactory.addComment(post, 1);
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .message("댓글 수정 테스트")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/board/posts/{postId}/{commentId}", post.getPostId(), commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateCommentRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updateComment",
                        pathParameters(
                                parameterWithName("postId").description("게시글 번호"),
                                parameterWithName("commentId").description("댓글 번호")
                        ),
                        requestFields(
                                fieldWithPath("message").description("댓글")
                        )));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/board/posts/{postId}", post.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comments[0].message").value("댓글 수정 테스트"));
    }

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("댓글 수정하기(포스트가 없을때)")
    void updateCommentFailBecausePostNotExist() throws Exception {
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .message("댓글 수정 테스트")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/board/posts/{postId}/{commentId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateCommentRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error").value("1101"))
                .andDo(print());
    }

    @Test
    @WithMockUser("TestUser1")
    @DisplayName("댓글 수정하기(댓글이 없을때)")
    void updateCommentFailBecauseCommentNotExist() throws Exception {
        Post post = this.postFactory.generatePost(1);
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .message("댓글 수정 테스트")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/board/posts/{postId}/{commentId}", post.getPostId(), 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateCommentRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("error").value("1201"))
                .andDo(print());
    }

    @Test
    @WithMockUser("TestUser2")
    @DisplayName("댓글 수정하기(내 댓글이 아닐때)")
    void updateCommentFailBecauseCommentIsNotMine() throws Exception {
        Post post = this.postFactory.generatePost(1);
        long commentId = this.commentFactory.addComment(post, 1);
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .message("댓글 수정 테스트")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/board/posts/{postId}/{commentId}", post.getPostId(), commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updateCommentRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("error").value("1002"))
                .andDo(print());
    }
}
