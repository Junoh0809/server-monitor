package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey key = Keys.hmacShaKeyFor(
            "this-is-a-very-long-secret-key-for-jwt-signing-12345".getBytes()
    );

    private final long expirationMs = 1000 * 60 * 60; // 1시간

    public String createToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(username) // 누구의 토큰인지
                .issuedAt(now) // 발급 시각
                .expiration(expiry) // 만료 시각(1시간뒤)
                .signWith(key) // 비밀 키로 서명
                .compact(); // 최종적으로 문자열 형태의 토큰으로 완성
    }
}
