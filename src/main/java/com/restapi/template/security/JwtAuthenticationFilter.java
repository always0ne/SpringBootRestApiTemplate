package com.restapi.template.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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

    /**
     * JWT 토큰 검증
     *
     * @param request     SubletRequest
     * @param response    SubletResponse
     * @param filterChain FilterChain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Claims claims = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (claims != null && jwtTokenProvider.validateToken(claims))
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(jwtTokenProvider.getAuthentication(claims));
        filterChain.doFilter(request, response);
    }
}
