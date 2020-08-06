package com.restapi.template.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.template.common.response.ErrorResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 인증 필터
 *
 * @author always0ne
 * @version 1.0
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    /**
     * JWT 토큰 검증
     * 만료된 토큰이 발견되었을 때, 만료된 토큰 응답 발생
     *
     * @param request     SubletRequest
     * @param response    SubletResponse
     * @param filterChain FilterChain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Claims claims = null;
        try {
            claims = jwtTokenProvider.resolveToken((HttpServletRequest) request);
            if (claims != null)
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(claims));
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write(this.objectMapper.writeValueAsString(
                    new ErrorResponse(HttpStatus.FORBIDDEN, "0007", "만료된 토큰입니다."))
            );
        }

    }
}
