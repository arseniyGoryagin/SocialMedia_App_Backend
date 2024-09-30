package com.arseniy.socialmediaapi.updates.controller;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.updates.domain.dto.UpdateResponse;
import com.arseniy.socialmediaapi.updates.domain.dto.UpdatesResponse;
import com.arseniy.socialmediaapi.updates.domain.model.Update;
import com.arseniy.socialmediaapi.updates.repository.UpdateRepository;
import com.arseniy.socialmediaapi.updates.service.UpdateService;
import com.arseniy.socialmediaapi.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/updates")
public class UpdateController {


    private final UpdateService updateService;



    @GetMapping()
    ResponseEntity<UpdatesResponse> getUpdates(@RequestParam("limit") Long limit, @RequestParam("offset") Long offset){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user  = (User) authentication.getPrincipal();

        List<Update> updates = updateService.getUpdates(user.getUsername(), limit, offset);

        return new ResponseEntity<>(new UpdatesResponse(updates), HttpStatus.OK);

    }


    /*
    @GetMapping("long-poll")
    DeferredResult<Update> getUpdatesLongPolling(@RequestParam("offset")Long offset){





        return
    }*/

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserResponse(UserException e){

        ErrorResponse error = new ErrorResponse();

        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }



}
