package com.arseniy.socialmediaapi.auth.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Register request")
public class RegisterRequest {



    @NotBlank
    @Size(min = 5, max = 30, message = "Username must be longer than 5 characters")
    @Schema(defaultValue = "Incorrect username", description = "Username пользователя", example = "12345Username")
    private String username;


    @NotBlank
    @Schema(defaultValue = "Incorrect email", description = "user email", example = "example@email.com")
    @Email
    private String email;


    @NotBlank
    @Schema(description = "User password", example = "password")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters long")
    private String password;


    @NotBlank(message = "name must not be blank")
    @Schema(description = "Name of the user", example = "password")
    @Size(min = 5, max = 30, message = "Username must be longer than 5 characters")
    private String name;


}
