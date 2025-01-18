package com.arseniy.socialmediaapi.auth;


import com.arseniy.socialmediaapi.auth.domain.LoginRequest;
import com.arseniy.socialmediaapi.auth.domain.RegisterRequest;
import com.arseniy.socialmediaapi.auth.domain.TokenResponse;
import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.UsernameAlreadyInUseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private  final AuthorizationService authService;



    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {
        String token = authService.register(request.getUsername(), request.getPassword(), request.getEmail(), request.getName());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }



    @PostMapping("/login")
    public ResponseEntity<TokenResponse> logIn(@Valid @RequestBody LoginRequest request){
        String token =  authService.login(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }



}
