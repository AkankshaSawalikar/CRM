package com.springproject.myproject.crmproject.security; //security package, focusing on authentication and authorization.

import io.jsonwebtoken.*; //Provides classes to create and parse JWTs (JSON Web Tokens).
import io.jsonwebtoken.security.Keys; //Utility to generate secure keys for signing tokens.
import org.springframework.beans.factory.annotation.Value; //Injects values from application properties
import org.springframework.stereotype.Component; //Marks this class as a Spring-managed bean.

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY; // Holds the secret key for signing JWTs
    private final long expirationTime; //Defines how long the JWT will remain valid
    private final Key key; //A Key object generated from the SECRET_KEY

    // Constructor with constructor injection
    public JwtUtil(@Value("${jwt.secret.key}") String secretKey, @Value("${jwt.expiration.time}") long expirationTime) {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("JWT secret key is not set. Please check the configuration.");
        }
        this.SECRET_KEY = secretKey;
        this.expirationTime = expirationTime;
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); //Converts the SECRET_KEY into a cryptographic Key object using Keys.hmacShaKeyFor()

        // Log the secret key for debugging purposes (remove in production)
        System.out.println("JWT Secret Key: " + SECRET_KEY);
    }

    // Methods for generating and validating JWT
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", "ROLE_" + role)  // Store as "ROLE_ADMIN" or "ROLE_SUPERADMIN"
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }
//    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha2Fua3NoYSIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzQ1MjIxMzY5LCJleHAiOjE3NDUyMjQ5Njl9.z1xQBwXh9Gp5Jr6dZxt11571KQSDhY2oZ7ckb4-CZsk

    // Extracting Username from Token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //Extracting All Claims
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //Token Expiration Check
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    //Token Validation
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
