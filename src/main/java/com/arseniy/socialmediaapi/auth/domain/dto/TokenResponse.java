package com.arseniy.socialmediaapi.auth.domain.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenResponse {

    private final String token;
    private final String refreshToken = "";

}
