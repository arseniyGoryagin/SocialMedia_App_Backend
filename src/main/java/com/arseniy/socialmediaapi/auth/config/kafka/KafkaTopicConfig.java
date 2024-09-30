package com.arseniy.socialmediaapi.auth.config.kafka;


import com.arseniy.socialmediaapi.constant.AppConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.hibernate.annotations.Comment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(AppConstants.UPDATE_TOPIC).build();
    }


}
