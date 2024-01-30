package ariefsyaifu.gymmem.util;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.access.token.base64key}")
    private String accessTokenBase64Key;

    @Value("${jwt.refresh.token.base64key}")
    private String refreshTokenBase64Key;

    @Value("${jwt.expiration.minutes}")
    private Integer expirationMinutes;

    public String generateAccessToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expirationMinutes))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenBase64Key)))
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expirationMinutes))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenBase64Key)))
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessTokenBase64Key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}