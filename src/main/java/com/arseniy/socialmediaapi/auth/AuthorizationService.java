package com.arseniy.socialmediaapi.auth;


import com.arseniy.socialmediaapi.auth.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.auth.exceptions.UsernameAlreadyInUseException;
import com.arseniy.socialmediaapi.jwt.JwtService;
import com.arseniy.socialmediaapi.jwt.TokenType;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {


    private final  UserRepository userRepository;
    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public String login(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtService.generateToken(username, TokenType.ACCESS);
    }


    public String register(String username,  String password, String email, String name) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {


        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyInUseException();
        }
        if(userRepository.existsByUsername(username)){
            throw new UsernameAlreadyInUseException();
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(User.Role.USER);
        newUser.setName(name);

        userRepository.save(newUser);
        return jwtService.generateToken(newUser.getUsername(), TokenType.ACCESS);

    }


}
