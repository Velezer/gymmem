package ariefsyaifu.gymmem.util;

import java.security.Key;
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

    private Key key;

    @Value("${jwt.base64key}")
    private String base64Key;

    @Value("${jwt.expiration.minutes}")
    private Integer expirationMinutes;

    // public JwtUtil() {
    // byte[] keyBytes = Decoders.BASE64.decode(base64Key);
    // this.key = Keys.hmacShaKeyFor(keyBytes);
    // }

    public String createToken(Map<String, Object> claims, String email) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Key);
        if (this.key == null) {
            this.key = Keys.hmacShaKeyFor(keyBytes);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expirationMinutes))
                .signWith(key)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(base64Key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}