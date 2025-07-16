package com.example.Pahana.Edu.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // generate random key
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 2; // 2 hours

    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract username from token (alias for consistency)
    public static String extractUsername(String token) {
        return getUsernameFromToken(token);
    }

    // Extract role from token
    public static String extractRole(String token) {
        return extractClaim(token, claims -> (String) claims.get("role"));
    }

    // Extract expiration date from token
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract any claim from token
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from token
    private static Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token: " + e.getMessage());
        }
    }

    // Check if token is expired
    public static Boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return true; // Consider invalid tokens as expired
        }
    }

    // Validate token against username
    public static Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false; // Invalid token
        }
    }

    // Validate token without username check (useful for general validation)
    public static Boolean isValidToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Get remaining time until token expires (in milliseconds)
    public static Long getTokenRemainingTime(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration.getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            return 0L;
        }
    }

    // Check if token will expire soon (within 1 hour)
    public static Boolean isTokenExpiringSoon(String token) {
        try {
            Long remainingTime = getTokenRemainingTime(token);
            return remainingTime < (1000 * 60 * 60); // 1 hour in milliseconds
        } catch (Exception e) {
            return true;
        }
    }
}