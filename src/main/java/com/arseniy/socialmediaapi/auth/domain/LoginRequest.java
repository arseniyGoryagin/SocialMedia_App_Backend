package com.arseniy.socialmediaapi.auth.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class LoginRequest {

    @NotBlank
    @Schema(defaultValue = "Incorrect username", description = "users username", example = "12345Username")
    private String username;

    @NotBlank
    @Schema(description = "User password", example = "password")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters long")
    private String password;

}
