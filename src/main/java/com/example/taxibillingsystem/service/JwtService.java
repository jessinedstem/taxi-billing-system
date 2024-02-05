package com.example.taxibillingsystem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

public class JwtService {
    public static final String Secret = "Secret";

    public String extractusername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirations(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction) {
        Claims claims = extractAllClaimes(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaimes(String token) {
        return Jwts.parserBuilder().setSigningKey(Secret).parseClaimsJws(to)

    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractusername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpirations(token).before(newDate());
    }


}
