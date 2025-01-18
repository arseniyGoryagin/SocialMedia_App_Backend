package com.arseniy.socialmediaapi.updates;


import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.updates.domain.UpdateResponse;
import com.arseniy.socialmediaapi.updates.domain.Update;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final UpdateRepository updateRepository;
    private final UserRepository userRepository;


    private UpdateResponse toUpdateResponseFromUpdate(Update update){

        var user = update.getUser();

        return UpdateResponse.builder()
                .date(update.getDate())
                .id(update.getId())
                .name(user.getName())
                .username(user.getUsername())
                .profilePicture(user.getProfilePicture())
                .type(update.getType().ordinal())
                .message(update.getMessage())
                .build();
    }

    public Page<UpdateResponse> getUserUpdatesUpdates(String username, Pageable pageable){
        return updateRepository.findByUser_UsernameOrderByDateDesc(username, pageable).map(this::toUpdateResponseFromUpdate);
    }

    public void makeUpdate(String forUsername, String fromUser,String message, Update.Type type) throws NoSuchException {

        User user = userRepository.findByUsername(forUsername).orElseThrow(() -> new NoSuchException("No such user"));
        User fromUserObject = userRepository.findByUsername(fromUser).orElseThrow(() -> new NoSuchException("No such user " + fromUser));

        var update = Update.builder()
                    .type(type)
                    .user(user)
                    .from(fromUserObject)
                    .date(LocalDateTime.now())
                    .message(message)
                    .build();


        updateRepository.save(update);
    }



}
