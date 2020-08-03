package com.restapi.template.security;

import com.restapi.template.common.BaseControllerTest;
import com.restapi.template.security.request.SignInRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("인증 테스트")
class SignInTest extends BaseControllerTest {

    @Test
    @Transactional
    @DisplayName("토큰 발급 받기(성공)")
    void signInSuccess() throws Exception {
        this.accountFactory.generateUser(1);
        SignInRequest signInRequest = SignInRequest.builder()
                .id("TestUser1")
                .password("password")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("signin"));
    }

    @Test
    @Transactional
    @DisplayName("토큰 발급 받기(실패)")
    void signInFailBecauseUserNotFound() throws Exception {
        SignInRequest signInRequest = SignInRequest.builder()
                .id("TestUser1")
                .password("password")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}