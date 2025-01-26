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
        e.printStackTrace();
        log.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e){


        Map<String, String> errors = new HashMap<>();

        BindingResult bindingResult =e.getBindingResult();

        List<FieldError> filedErrors = bindingResult.getFieldErrors();


        for(FieldError  filedError : filedErrors){
            errors.put(filedError.getField(), filedError.getDefaultMessage());
        }


        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
    }



}
