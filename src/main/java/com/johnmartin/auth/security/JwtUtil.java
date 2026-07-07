package com.johnmartin.auth.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.johnmartin.auth.constants.error.SystemErrorConstants;
import com.johnmartin.auth.utilities.LoggerUtility;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;

@Component
public class JwtUtil {

    private static final Class<JwtUtil> clazz = JwtUtil.class;
    private final SecretKey secretKey;
    private final long expirationMillis;

    public JwtUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long expirationMillis) {
        if (StringUtils.isBlank(secretKey)) {
            throw new IllegalArgumentException(SystemErrorConstants.JWT_SECRET_CANNOT_BE_NULL_OR_EMPTY);
        }

        byte[] decodedKey = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(String userId) {
        LoggerUtility.d(clazz, "Execute method: [generateToken]");
        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException(SystemErrorConstants.USER_ID_CANNOT_BE_BLANK_WHEN_GENERATING_TOKEN);
        }

        LoggerUtility.d(clazz, "userId is valid, will generate token");

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                   .setSubject(userId)
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(secretKey)
                   .compact();
    }

    public String extractUserId(String token) {
        LoggerUtility.d(clazz, "Execute method: [extractUserId]");

        if (StringUtils.isBlank(token)) {
            LoggerUtility.d(clazz, "Token is null or blank, will return null");
            return null;
        }

        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public long getExpirationSeconds() {
        return expirationMillis / 1000;
    }
}
