package com.arseniy.socialmediaapi.user.services;


import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;

        public UserDetailsService userDetailsService() {
                return this::getByUsername;
        }

        public UserDetails getByUsername(String username) {
               return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no such user"));
        }




}
