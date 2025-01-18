package com.arseniy.socialmediaapi.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchException(NoSuchException e){
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowedException (NotAllowedException e){
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }




    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<Map<String, String>> handleEmailException(EmailAlreadyInUseException e){

        log.error(e.getMessage());

        Map<String, String> error = new HashMap<>();

        error.put("email", "Email is already in use");

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);

    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<Map<String, String>> handleUsernameException(UsernameAlreadyInUseException e){

        log.error(e.getMessage());

        Map<String, String> error = new HashMap<>();

        error.put("username", "Username is already in use");

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e){

        log.error(e.getMessage());

        Map<String, String> errors = new HashMap<>();

        BindingResult bindingResult =e.getBindingResult();

        List<FieldError> filedErrors = bindingResult.getFieldErrors();


        for(FieldError  filedError : filedErrors){
            errors.put(filedError.getField(), filedError.getDefaultMessage());
        }


        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }



}
