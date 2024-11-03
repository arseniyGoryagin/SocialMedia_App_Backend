package com.arseniy.socialmediaapi.user.controllers;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.posts.domain.dto.PostResponse;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.services.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}/followers")
    public ResponseEntity<Page<UserResponse>> getUserFollowers(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User me = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size);
        log.info("size = " + size);
        log.info("pagewnum = " + page);
        log.info("folowers " + userService.getUserFollowersList(username, pageable).toString());
        log.info("folowers page " + userService.getUserFollowers(username, pageable).getContent());


        var userRespPage  = userService.getUserFollowers(username, pageable).map(resp -> {
                    resp.setIsOwn(Objects.equals(me.getUsername(), resp.getUsername()));
                    return resp;
                }
        );


        return new ResponseEntity<>(userRespPage, HttpStatus.OK);
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<Page<UserResponse>> getUserFollows(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) throws UserException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User me = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size);

        log.info("size = " + size);
        log.info("pagewnum = " + page);
        log.info("folowing " + userService.getUserFollows(username, pageable).getContent().toString());


        var userRespPage  = userService.getUserFollowers(username, pageable).map(resp -> {
                    resp.setIsOwn(Objects.equals(me.getUsername(), resp.getUsername()));
                    return resp;
                }
        );

        return new ResponseEntity<>(userRespPage, HttpStatus.OK);
    }



    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username) throws UserException {;


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User me = (User) authentication.getPrincipal();

        Optional<User> user = userService.findByUsername(username);

        if(user.isEmpty()){
            throw new UserException("No such user");
        }

        var resp = userService.getUserAsResponse(user.get().getUsername(), me.getUsername());

        if(Objects.equals(resp.getUsername(), me.getUsername())){
            resp.setIsOwn(true);
        }else{
            resp.setIsOwn(false);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUser(@RequestParam("username") String searchUsername, @RequestParam("page") int page, @RequestParam("size") int size){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(page, size);

        Page<UserResponse> users  = userService.searchUsers(searchUsername, user.getUsername(), pageable );

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        var resp = userService.getUserAsResponse(user.getUsername(), null);
        resp.setIsOwn(true);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }



    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException e){

        log.info(e.getLocalizedMessage());

        ErrorResponse resp  = new ErrorResponse();
        resp.setMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUserException(Exception e){

        log.error(e.getLocalizedMessage());

        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
