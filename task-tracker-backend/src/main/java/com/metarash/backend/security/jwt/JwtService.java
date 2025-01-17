package com.metarash.backend.security.jwt;

import com.metarash.backend.dto.JwtAuthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final Logger LOGGER = LogManager.getLogger(JwtService.class);

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
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
        return generateToken(email, Duration.ofMinutes(1));
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, Duration.ofDays(1));
    }

    public String getEmailFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            LOGGER.error("Invalid JWT token: ", e);
        }
        return false;
    }

    private JwtAuthenticationDto createJwtAuthDto(String email, String refreshToken) {
        JwtAuthenticationDto jwtAuthDto = new JwtAuthenticationDto();
        jwtAuthDto.setToken(generateJwtToken(email));
        jwtAuthDto.setRefreshToken(refreshToken);
        return jwtAuthDto;
    }

    private String generateToken(String email, Duration duration) {
        Date date = Date.from(
                LocalDateTime.now()
                        .plus(duration)
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
        return Jwts.builder()
                .subject(email)
                .expiration(date)
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
}