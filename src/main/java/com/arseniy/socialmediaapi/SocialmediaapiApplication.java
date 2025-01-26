package com.arseniy.socialmediaapi;

import com.arseniy.socialmediaapi.jwt.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SocialmediaapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialmediaapiApplication.class, args);
	}
}
