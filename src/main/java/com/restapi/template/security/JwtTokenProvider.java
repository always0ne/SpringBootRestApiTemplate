package com.restapi.template.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

/**
 * JWT 토큰 처리기
 *
 * @author always0ne
 * @version 1.0
 */
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    /**
     * Secret Key
     */
    private Key key;
    /**
     * AccessToken 유효시간(1시간)
     */
    private final long tokenValidMilSecond = 1000L * 60 * 60; // 1시간

    private final JwtUserDetailService jwtUserDetailService;

    @PostConstruct
    protected void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * AccessToken 생성
     *
     * @param userId 발급할 사용자의 아이디
     * @param roles  사용자에게 허용할 권한
     * @return AccessToken
     */
    public String createToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilSecond))
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Http Request 에서 JWT 토큰의 데이터 추출
     *
     * @param req Http 요청
     * @return 토큰 데이터
     */
    public Claims resolveToken(HttpServletRequest req) {
        String token = req.getHeader("Authentication");
        if (token == null)
            return null;
        else
            token.replace("Bearer ", "");

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * JWT 토큰의 유효시간 검증
     *
     * @param claims JWT 토큰 데이터
     * @return 토큰의 유효 여부
     */
    public boolean validateToken(Claims claims) {
        return !claims
                .getExpiration().before(new Date());
    }

    /**
     * 인증 발급
     *
     * @param claims JWT 토큰 데이터
     * @return Spring Security 인증토큰
     */
    public Authentication getAuthentication(Claims claims) {
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(this.getUserId(claims));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /**
     * JWT 토큰 데이터 에서 UserID 추출
     *
     * @param claims JWT 토큰 데이터
     * @return UserId
     */
    private String getUserId(Claims claims) {
        return claims
                .getSubject();
    }
}