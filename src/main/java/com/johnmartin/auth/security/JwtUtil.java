package com.johnmartin.auth.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.johnmartin.auth.constants.AppStrings;
import com.johnmartin.auth.constants.api.ApiErrorMessages;
import com.johnmartin.auth.entities.UserEntity;
import com.johnmartin.auth.exception.UnauthorizedException;
import com.johnmartin.auth.repository.UserRepository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMillis;

    @Autowired
    private UserRepository userRepository;

    public JwtUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long expirationMillis) {

        if (StringUtils.isBlank(secretKey)) {
            throw new IllegalArgumentException(AppStrings.JWT_SECRET_CANNOT_BE_NULL_OR_EMPTY);
        }

        byte[] decodedKey = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException(AppStrings.EMAIL_CANNOT_BE_BLANK_WHEN_GENERATING_TOKEN);
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                   .setSubject(email)
                   .setIssuedAt(now)
                   .setExpiration(expiryDate)
                   .signWith(secretKey)
                   .compact();
    }

    public String extractEmail(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        return getClaimsSubject(token);
    }

    private String getClaimsSubject(String token) {
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

    public UserEntity validateTokenAndGetUser(String token) {
        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException(ApiErrorMessages.USER_IS_NOT_AUTHENTICATED);
        }

        try {
            String userId = getClaimsSubject(token);
            return userRepository.findById(userId)
                                 .orElseThrow(() -> new UnauthorizedException(ApiErrorMessages.USER_NOT_FOUND));

        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException(ApiErrorMessages.INVALID_TOKEN);
        }
    }

}
