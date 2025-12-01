package co.kr.grouppurchace.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityInMs;
    private final long refreshTokenValidityInMs;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-seconds}") long accessTokenValidity,
            @Value("${jwt.refresh-expiration-seconds}") long refreshTokenValidity
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMs = accessTokenValidity * 1000L;
        this.refreshTokenValidityInMs = refreshTokenValidity * 1000L;
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername(), accessTokenValidityInMs);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails.getUsername(), refreshTokenValidityInMs);
    }

    private String buildToken(String subject, long validityInMs) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
