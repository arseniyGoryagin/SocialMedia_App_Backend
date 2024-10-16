package com.arseniy.socialmediaapi.auth.controllers;


import com.arseniy.socialmediaapi.auth.domain.dto.RegisterRequest;
import com.arseniy.socialmediaapi.auth.domain.dto.TokenResponse;
import com.arseniy.socialmediaapi.auth.domain.dto.*;
import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.auth.services.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private  final AuthorizationService authService;



    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) throws UserException {

        log.info("Request " + request.toString());

        String token = authService.register(request.getUsername(), request.getPassword(), request.getEmail());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }



    @PostMapping("/login")
    public ResponseEntity<TokenResponse> logIn(@Valid @RequestBody LoginRequest request){
        String token =  authService.login(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }



    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e){

        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getLocalizedMessage());

        return new ResponseEntity< ErrorResponse >(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e){
        return new ResponseEntity< >(e.getLocalizedMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUserException(Exception e){

        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());

        return new ResponseEntity< ErrorResponse >(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
