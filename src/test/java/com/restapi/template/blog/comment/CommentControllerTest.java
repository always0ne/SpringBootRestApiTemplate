package com.restapi.template.blog.comment;

import com.restapi.template.blog.comment.request.AddCommentRequest;
import com.restapi.template.blog.comment.request.UpdateCommentRequest;
import com.restapi.template.blog.post.Post;
import com.restapi.template.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("댓글 테스트")
class CommentControllerTest extends BaseControllerTest {

    @Test
    @Transactional
    @DisplayName("댓글 쓰기")
    void saveComment() throws Exception {
        Post post = this.getneratePost(1);
        AddCommentRequest addCommentRequest = AddCommentRequest.builder()
                .commenterId("댓글 작성자")
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
                                fieldWithPath("commenterId").description("댓글 작성자 Id"),
                                fieldWithPath("message").description("댓글")
                        )));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}", post.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comments[0].commenterId").value("댓글 작성자"))
                .andExpect(jsonPath("comments[0].message").value("댓글 테스트"));
    }


    @Test
    @Transactional
    @DisplayName("댓글 수정하기")
    void updateComment() throws Exception {
        Post post = this.getneratePost(1);
        long commentId = this.addComment(post, 1);
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .message("댓글 수정 테스트")
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/blog/posts/{postId}/{commentId}", post.getPostId(), commentId)
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
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}", post.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("comments[0].commenterId").value("댓글 작성자1"))
                .andExpect(jsonPath("comments[0].message").value("댓글 수정 테스트"));
    }


    @Test
    @Transactional
    @DisplayName("댓글 지우기")
    void deleteComment() throws Exception {

        Post post = this.getneratePost(1);
        long commentId = this.addComment(post, 1);
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