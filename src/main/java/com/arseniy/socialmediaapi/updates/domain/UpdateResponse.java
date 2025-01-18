package com.arseniy.socialmediaapi.updates.domain;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateResponse {

    Long id;

    int type;

    String name;
    String username;
    String profilePicture;

    String message;

    LocalDateTime date;

}
