package com.restapi.template.security;

import com.restapi.template.common.BaseControllerTest;
import com.restapi.template.security.request.RefreshRequest;
import com.restapi.template.security.response.SignInResponse;
import org.junit.jupiter.api.Disabled;
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
    void refreshTokenSuccess() throws Exception {
        SignInResponse signInResponse = accountFactory.generateUser(1);
        RefreshRequest refreshRequest = RefreshRequest.builder()
                .refreshToken(signInResponse.getRefreshToken())
                .build();
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("refresh"));
    }

    @Test
    @Disabled       // Admin Api 개발 이후 활성화
    @Transactional
    @DisplayName("토큰 재발급 받기(계정이 제제당했을 때)")
    void refreshTokenFailBecauseDifferentUserToken() throws Exception {
        SignInResponse signInResponse1 = accountFactory.generateUser(1);
        RefreshRequest refreshRequest = RefreshRequest.builder()
                .refreshToken(signInResponse1.getRefreshToken())
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
    void refreshTokenFailBecauseMalFormed() throws Exception {
        SignInResponse signInResponse = accountFactory.generateUser(1);
        RefreshRequest refreshRequest = RefreshRequest.builder()
                .refreshToken(signInResponse.getRefreshToken() + "e")
                .build();

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}