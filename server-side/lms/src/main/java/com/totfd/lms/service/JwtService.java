package com.totfd.lms.service;

import com.totfd.lms.entity.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long EMAIL_VERIFICATION_EXPIRATION = 1000 * 60 * 60;  // 1 hour

    // Generate regular JWT token for user login
    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().getName());  // updated from roles list to single role

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hrs for regular token
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Generate JWT token for email verification with a 1-hour expiration
    public String generateEmailVerificationToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EMAIL_VERIFICATION_EXPIRATION)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // Validate token and get email (for email verification)
    public String validateAndGetEmail(String token) {
        String email = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return email;
    }

    // Check if the email verification token is expired
    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }
}
