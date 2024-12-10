package com.example.taskmanager.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final long jwtExpirationMs = 86400000; // 1 jour

    // Générer le JWT
    @SuppressWarnings("deprecation")
    public String generateJwtToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Extraire l'utilisateur du JWT
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()  // Utiliser parserBuilder() au lieu de parser() qui est obsolète
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valider le JWT
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()  // Utiliser parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT non valide : " + e.getMessage());
        }
        return false;
    }
}
