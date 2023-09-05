package com.example.marketbackend.security;

import com.example.marketbackend.entity.User;
import com.example.marketbackend.exception.InvalidTokenException;
import com.example.marketbackend.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtProvider(@Value("${jwt.secret}") String key, CustomUserDetailsService customUserDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.customUserDetailsService = customUserDetailsService;
    }

    public String createAccessToken(User user) {
        String userId = String.valueOf(user.getId());
        Claims claims = Jwts.claims().setSubject(userId);

        claims.put("name", user.getUserName());

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + 1_800_000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String userId = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token expired", e);
        } catch (SignatureException e) {
            throw new InvalidTokenException("Invalid token", e);
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String token = getToken(request);

        if(token != null && token.startsWith("Bearer "))
            return token.substring(7);

        return null;
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
