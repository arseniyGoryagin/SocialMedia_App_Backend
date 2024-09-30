package com.arseniy.socialmediaapi.updates.domain.dto;


import com.arseniy.socialmediaapi.updates.domain.model.Update;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdatesResponse {
    private final List<Update> updates;

}
