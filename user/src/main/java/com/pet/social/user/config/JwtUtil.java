//package com.pet.social.user.util;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    private static final String SECRET = "Y0uRSup3rLongAndS3cur3SecretKeyForJWTAuth1234567890123456789012345678901234567890";
//    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
//    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 часов
//
//    public String extractUsername(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration()
//                .before(new Date());
//    }
//
//    public boolean validateToken(String token, String username) {
//        String extracted = extractUsername(token);
//        return extracted.equals(username) && !isTokenExpired(token);
//    }
//}
