package com.arseniy.socialmediaapi.kafka;


import com.arseniy.socialmediaapi.constant.AppConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(AppConstants.UPDATE_TOPIC).build();
    }


}
