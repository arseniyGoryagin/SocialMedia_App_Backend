package com.arseniy.socialmediaapi.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt")
@Data
public class JwtConfig {

    private String tokenSecret;
    private Long expAccess;
    private Long expReset;
    private Long expRefresh;

}
