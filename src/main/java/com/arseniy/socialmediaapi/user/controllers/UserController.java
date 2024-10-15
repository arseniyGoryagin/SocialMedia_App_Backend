package com.arseniy.socialmediaapi.user.controllers;


import com.arseniy.socialmediaapi.auth.domain.dto.ErrorResponse;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.services.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username){

        log.info("in controller =- " + username);

        User user = (User) userService.getByUsername(username);


        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .description(user.getDescription())
                .profilePicture(user.getProfilePicture())
                .build();


        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .description(user.getDescription())
                .profilePicture(user.getProfilePicture())
                .build();

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException e){
        ErrorResponse resp  = new ErrorResponse();
        resp.setMessage(e.getLocalizedMessage());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUserException(Exception e){

        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
