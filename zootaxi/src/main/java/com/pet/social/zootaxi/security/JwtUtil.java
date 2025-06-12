package com.pet.social.zootaxi.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    private static final String SECRET = "aaf2c09bca7c4b0d8464b49961a14797f5a274c7ea7f45df8c606fc5488f86e7d28b7c939e8918b879d5a1e2b0e4103d";
    private Key key;
    private JwtParser parser;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
        this.parser = Jwts.parser().build();
    }

    public String extractUsername(String token) {
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parser.parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
