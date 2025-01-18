package com.arseniy.socialmediaapi.auth;


import com.arseniy.socialmediaapi.auth.domain.LoginRequest;
import com.arseniy.socialmediaapi.auth.domain.RegisterRequest;
import com.arseniy.socialmediaapi.auth.domain.TokenResponse;
import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.UsernameAlreadyInUseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthorizationService authService;


    @Operation(summary = "Register user")
    @ApiResponse(responseCode = "200", description = "Register success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {
        String token = authService.register(request.getUsername(), request.getPassword(), request.getEmail(), request.getName());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }


    @Operation(summary = "User login")
    @ApiResponse(responseCode = "200", description = "Login user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> logIn(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK);
    }


}
