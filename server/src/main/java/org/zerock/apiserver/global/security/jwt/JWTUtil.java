package org.zerock.apiserver.global.security.jwt;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    private SecretKey key;


    private static final long ACCESS_TOKEN_MAX_AGE = 1000L * 60 * 60 * 24; // 24시간
    private static final long REFRESH_TOKEN_MAX_AGE = 1000L * 60 * 60 * 24 * 30; // 30일



    @PostConstruct
    private void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public String createAccessToken(Long userId) {
        return createToken(String.valueOf(userId), ACCESS_TOKEN_MAX_AGE);
    }

    public String createRefreshToken(Long userId) {
        return createToken(String.valueOf(userId), REFRESH_TOKEN_MAX_AGE);
    }

    public String createToken(String userId, long maxAge) {
        return Jwts.builder()
            .subject(userId)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + maxAge))
            .signWith(key)
            .compact();
    }


    public String getUserIdFromToken(String token) {
        return parseClaims(token).getPayload().getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return null;
    }

    public void validateToken(String token) {
        parseClaims(token);
    }


    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token);
    }

    public int getRefreshTokenMaxAgeInSeconds() {
        return (int) (REFRESH_TOKEN_MAX_AGE / 1000);
    }
}
