package com.metarash.backend.security.jwt;

import com.metarash.backend.config.JwtProperties;
import com.metarash.backend.model.dto.JwtAuthenticationDto;
import com.metarash.backend.utils.DurationUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties properties;

    @Value("${jwt.access-token-expiration}")
    private String accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private String refreshTokenExpiration;

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getSignInKey() {
        return secretKey;
    }

    public JwtAuthenticationDto generateAuthToken(String email) {
        return createJwtAuthDto(email, generateRefreshToken(email));
    }

    public JwtAuthenticationDto refreshBaseToken(String email, String refreshToken) {
        return createJwtAuthDto(email, refreshToken);
    }

    public String generateJwtToken(String email) {
        return generateToken(email, accessTokenExpiration);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, refreshTokenExpiration);
    }

    public String getEmailFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public boolean validateJwtToken(String token) {
        parseToken(token);
        return true;
    }

    private JwtAuthenticationDto createJwtAuthDto(String email, String refreshToken) {
        JwtAuthenticationDto jwtAuthDto = new JwtAuthenticationDto();
        jwtAuthDto.setToken(generateJwtToken(email));
        jwtAuthDto.setRefreshToken(refreshToken);
        return jwtAuthDto;
    }

    private String generateToken(String email, String durationStr) {
        Duration duration = DurationUtils.parseDuration(durationStr);
        log.info("trying to parse data and building token");
        Date date = Date.from(
                LocalDateTime.now()
                        .plus(duration)
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
        log.info("building token with date: {}", date);
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .claim("token_type", durationStr.equals(refreshTokenExpiration) ? "refresh" : "access")
                .signWith(getSignInKey())
                .compact();
    }

    private Claims parseToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidRefreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            return false;
        }

        try {
            Claims claims = parseToken(refreshToken);

            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                return false;
            }

            String tokenType = claims.get("token_type", String.class);
            if (!"refresh".equals(tokenType)) {
                return false;
            }

            String email = claims.getSubject();
            return email != null && !email.isBlank();
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid refresh token: {}", e.getMessage());
            return false;
        }
    }
}