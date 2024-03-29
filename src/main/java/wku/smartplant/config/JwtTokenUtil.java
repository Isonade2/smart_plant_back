package wku.smartplant.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "여기에는안전한비밀키를넣어주세요비밀키는적어도256비트여야합니다"; // 실제 환경에서는 환경 변수 등에서 가져오는 것이 좋습니다.
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // Jwt Token 발급
    public static String createToken(String memberId, long expireTimeMs) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Claims에서 memberId 꺼내기
    public static String getMemberId(String token) {
        return extractClaims(token).get("memberId", String.class);
    }

    public static boolean isExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // token 파싱
    private static Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenValidationException("Token expired");
        } catch (JwtException e) {
            throw new TokenValidationException("Error in JWT processing: " + e.getMessage());
        }
    }

    public static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String message) {
            super(message);
        }
    }
}
