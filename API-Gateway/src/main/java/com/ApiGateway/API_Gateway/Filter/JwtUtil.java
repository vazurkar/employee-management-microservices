package com.ApiGateway.API_Gateway.Filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class JwtUtil {


    private static final String SECRET_KEY =
            "SGVsbG9UaGlzSXNBVGVzdFNlY3JldEtleUZvckpXVFRlc3RpbmcxMjM0NTY3ODkw";



    private SecretKey getKey() {

        byte[] bytes = Base64.getDecoder().decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(bytes);
    }
    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseClaimsJws(token);
    }
}
