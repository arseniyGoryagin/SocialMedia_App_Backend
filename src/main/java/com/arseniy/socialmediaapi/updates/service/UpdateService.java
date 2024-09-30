package com.arseniy.socialmediaapi.updates.service;


import com.arseniy.socialmediaapi.constant.AppConstants;
import com.arseniy.socialmediaapi.updates.domain.dto.UpdateMessage;
import com.arseniy.socialmediaapi.updates.domain.model.Update;
import com.arseniy.socialmediaapi.updates.repository.UpdateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final KafkaTemplate<String, Update> kafkaTemplate;
    private final UpdateRepository updateRepository;

    public List<Update> getUpdates(String username, Long limit, Long offset){
        return updateRepository.getAllUserUpdates(username, limit, offset);
    }


    public void sendUpdate(Update update){
            kafkaTemplate.send(AppConstants.UPDATE_TOPIC,update);
            updateRepository.save(update);
    }



}
