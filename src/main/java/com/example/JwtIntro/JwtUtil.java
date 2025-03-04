package com.example.JwtIntro;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "EnMycketHemligNyckelSomHarMInst32Tecken"; //hemlig nyckel som bör läggas i en enviroment varibel fil
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // giltig i 30 min
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String validateToken(String token) {

      try {
        Jws<Claims> claimsJws =Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        return claimsJws.getPayload().getSubject();
      } catch (Exception e) {
          return null;
      }
    }
}















