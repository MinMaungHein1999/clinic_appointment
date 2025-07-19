package com.clinic.appointment.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private Long EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractEmail(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(jwt).getBody();
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        return true;
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String email = userDetails.getUsername();
        return createToken(claims,email);
    }

    public String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSignedKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}