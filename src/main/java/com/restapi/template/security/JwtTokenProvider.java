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

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private Key key;

    private final long tokenValidMilSecond = 1000L * 60 * 60; // 1시간

    private final JwtUserDetailService jwtUserDetailService;

    @PostConstruct
    protected void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(String userId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilSecond))
                .signWith(this.key, SignatureAlgorithm.RS256)
                .compact();
    }

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


    public boolean validateToken(Claims claims) {
        return !claims
                .getExpiration().before(new Date());
    }

    public Authentication getAuthentication(Claims claims) {
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(this.getUserId(claims));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserId(Claims claims) {
        return claims
                .getSubject();
    }
}