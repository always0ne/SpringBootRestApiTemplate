package com.restapi.template.community.post;

import com.restapi.template.common.BaseControllerTest;
import com.restapi.template.community.post.request.ModifyPostRequest;
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

@DisplayName("게시글 수정 테스트")
class UpdatePostTest extends BaseControllerTest {

    @Test
    @Transactional
    @WithMockUser("TestUser1")
    @DisplayName("포스트 수정(성공)")
    void updatePostSuccess() throws Exception {
        Post post = this.postFactory.generatePost(1);
        ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder()
                .title("수정된 포스트")
                .body("포스트 수정 테스트입니다.")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/blog/posts/{postId}", post.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyPostRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updatePost",
                        pathParameters(
                                parameterWithName("postId").description("게시글 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시글 제목"),
                                fieldWithPath("body").description("게시글 내용")
                        )
                ));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/blog/posts/{postId}", post.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("수정된 포스트"))
                .andExpect(jsonPath("body").value("포스트 수정 테스트입니다."))
                .andExpect(jsonPath("views").value(1));
    }

    @Test
    @Transactional
    @WithMockUser("TestUser1")
    @DisplayName("포스트 수정(포스트가 없을때)")
    void updatePostFailBecausePostNotExist() throws Exception {
        ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder()
                .title("수정된 포스트")
                .body("포스트 수정 테스트입니다.")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/blog/posts/{postId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyPostRequest)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Transactional
    @WithMockUser("TestUser2")
    @DisplayName("포스트 수정(내 포스트가 아닐때)")
    void updatePostFailBecausePostIsNotMine() throws Exception {
        Post post = this.postFactory.generatePost(1);
        ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder()
                .title("수정된 포스트")
                .body("포스트 수정 테스트입니다.")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/blog/posts/{postId}", post.getPostId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(modifyPostRequest)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}
