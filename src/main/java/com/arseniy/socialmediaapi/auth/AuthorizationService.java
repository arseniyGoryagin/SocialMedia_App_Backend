package com.arseniy.socialmediaapi.auth;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.UsernameAlreadyInUseException;
import com.arseniy.socialmediaapi.jwt.JwtService;
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
        return jwtService.generateToken(username);
    }


    public String register(String username,  String password, String email, String name) throws EmailAlreadyInUseException, UsernameAlreadyInUseException {

        // Validate if user have is already registered

        Optional<User> user =  userRepository.findByEmail(email);

        if(user.isPresent()){
            throw new UsernameAlreadyInUseException();
        }

        user = userRepository.findByUsername(username);


        if(user.isPresent()){
            throw new EmailAlreadyInUseException();
        }


        // Add user to db

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(User.Role.USER);
        newUser.setName(name);

        userRepository.save(newUser);

        // Generate token

        return jwtService.generateToken(newUser.getUsername());



    }




}
