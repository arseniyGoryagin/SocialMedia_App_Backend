package com.arseniy.socialmediaapi.jwt;

public interface JwtService {

    String generateToken(String username);

    void validateToken(String token);

    String getUsernameFromToken(String token);

}
