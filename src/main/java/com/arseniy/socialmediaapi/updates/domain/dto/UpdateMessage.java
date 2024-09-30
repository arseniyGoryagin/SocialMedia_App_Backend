package com.arseniy.socialmediaapi.updates.domain.dto;


import com.arseniy.socialmediaapi.updates.domain.model.Update;
import lombok.Data;

@Data
public class UpdateMessage {

    private String to_username;
    private Update update;

}
