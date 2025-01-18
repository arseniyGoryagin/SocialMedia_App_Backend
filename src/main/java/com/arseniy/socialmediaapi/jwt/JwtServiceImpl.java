package com.arseniy.socialmediaapi.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
@Slf4j
public class JwtServiceImpl implements JwtService {



    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    @Override
    public String generateToken(String username) {

        log.info("GENERERATED WITH NAME "  + username);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 *60 *60 *10))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public void validateToken(String token) {
        Jwts.parser().setSigningKey(secretKey).build().parseSignedClaims(token);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return  Jwts.parser().setSigningKey(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }



}
