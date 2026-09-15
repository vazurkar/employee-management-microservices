package com.AuthService.AuthService.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY =
            "SGVsbG9UaGlzSXNBVGVzdFNlY3JldEtleUZvckpXVFRlc3RpbmcxMjM0NTY3ODkw";

    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("email", "am@Gmail.com");

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {

        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }
}