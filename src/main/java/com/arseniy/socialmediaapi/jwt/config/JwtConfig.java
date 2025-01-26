package com.arseniy.socialmediaapi.jwt.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;


@ConfigurationProperties("jwt")
@Data
@Component
public class JwtConfig {


    private String secret;
    private Long expAccess;
    private Long expReset;
    private Long expRefresh;

}
