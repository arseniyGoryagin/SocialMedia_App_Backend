package com.arseniy.socialmediaapi.kafka.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("kafka")
@Data
public class KafkaConfig {




}
