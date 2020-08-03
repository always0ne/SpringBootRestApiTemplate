package com.restapi.template.security;

import com.restapi.template.common.BaseControllerTest;
import com.restapi.template.security.request.RefreshRequest;
import com.restapi.template.security.response.SignInResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("토큰 재발급 테스트")
class RefreshTest extends BaseControllerTest {

    @Test
    @Transactional
    @DisplayName("토큰 재발급 받기(성공)")
    void signInSuccess() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(accountFactory.generateUser(1))))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("refresh"));
    }

    @Test
    @Transactional
    @DisplayName("토큰 재발급 받기(토큰의 계정 정보가 다를 때 실패)")
    void signInFailBecauseDifferentUserToken() throws Exception {
        SignInResponse signInResponse1 = accountFactory.generateUser(1);
        SignInResponse signInResponse2 = accountFactory.generateUser(2);
        RefreshRequest refreshRequest = RefreshRequest.builder()
                .accessToken(signInResponse1.getAccessToken())
                .refreshToken(signInResponse2.getAccessToken())
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("토큰 재발급 받기(토큰이 깨졌을 때 실패)")
    void signInFailBecauseMalFormed() throws Exception {
        SignInResponse signInResponse = accountFactory.generateUser(1);
        RefreshRequest refreshRequest = RefreshRequest.builder()
                .accessToken(signInResponse.getAccessToken())
                .refreshToken(signInResponse.getAccessToken() + "e")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}