package org.thejava.assignment.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component("jwtTokenUtil")
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public  String generateJwtToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 86400000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public  boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public  String getUserFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();

    }
}
