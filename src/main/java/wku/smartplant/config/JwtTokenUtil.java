package wku.smartplant.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    // Jwt Token 발급
    public static String createToken(String memberId, String key, long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // Claims에서 memberId 꺼내기
    public static String getMemberId(String token, String secretKey) {
        return extractClaims(token, secretKey).get("memberId").toString();
    }

    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token,secretKey).getExpiration();
        //token 만료 날짜가 지금보다 이전이면 true (즉 만료됨)
        return expiredDate.before(new Date());
    }

    // secretKey를 사용해 token 파싱
    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
