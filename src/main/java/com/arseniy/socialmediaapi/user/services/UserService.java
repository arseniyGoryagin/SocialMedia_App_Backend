package com.arseniy.socialmediaapi.user.services;


import com.arseniy.socialmediaapi.auth.domain.exceptions.UserException;
import com.arseniy.socialmediaapi.follwers.repository.FollowerRepository;
import com.arseniy.socialmediaapi.like.domain.model.Like;
import com.arseniy.socialmediaapi.posts.domain.dto.PostResponse;
import com.arseniy.socialmediaapi.posts.domain.model.Post;
import com.arseniy.socialmediaapi.user.domain.dto.UserResponse;
import com.arseniy.socialmediaapi.user.domain.model.User;
import com.arseniy.socialmediaapi.user.domain.projection.UserResponseProjection;
import com.arseniy.socialmediaapi.user.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.net.ContentHandler;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


        private UserResponse projToUserResponse(UserResponseProjection u){
                return UserResponse.builder().
                        id(u.getId())
                        .name(u.getName())
                        .profilePicture(u.getProfilePicture())
                        .description(u.getDescription())
                        .followsCount(u.getFollowsCount())
                        .followerCount(u.getFollowerCount())
                        .username(u.getUsername())
                        .build();
        }


        private final UserRepository userRepository;
        private final FollowerRepository followerRepository;

        public UserDetailsService userDetailsService() {
                return this::getByUsername;
        }

        public UserDetails getByUsername(@NotNull String username) {
                log.info("username == " + username);
               return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no such user"));
        }

        public Optional<User> findByUsername(@NotNull String username) {

              return userRepository.findByUsername(username);
        }

        public UserResponse getUserAsResponse(@NotNull String username, String currentUser){

                var userResponseProjection = userRepository.getUserAsResponse(username);
                var user = projToUserResponse(userResponseProjection);

                if(currentUser == null){
                        user.setIsFollowing(false);
                        return user;
                }

                user.setIsFollowing(followerRepository.isFollowing(username, currentUser));
                return user;
        }

        public Page<UserResponse> searchUsers(@NotNull String usernameSearch, @NotNull String currentUser, Pageable pageable ) {

                var userResponsePage = userRepository.searchUsers(usernameSearch,currentUser,pageable).map(this::projToUserResponse);

                userResponsePage.map(userResponse -> {
                        userResponse.setIsOwn(false);
                       userResponse.setIsFollowing(followerRepository.isFollowing(usernameSearch, currentUser));
                       return userResponse;
                });

                return userResponsePage;

        }


        public Page<UserResponse> getUserFollowers(String username, Pageable pageable) throws UserException {

                Optional<User> user = userRepository.findByUsername(username);

                if(user.isEmpty()){
                        throw new UserException("No such user");
                }

                return followerRepository.getUserFollowers(username, pageable).map(this::projToUserResponse).map(resp -> {resp.setIsFollowing(followerRepository.isFollowing(username, resp.getUsername()));return resp;});
        }

        public Page<UserResponse> getUserFollows(String username, Pageable pageable) throws UserException {

                Optional<User> user = userRepository.findByUsername(username);

                if(user.isEmpty()){
                        throw new UserException("No such user");
                }

                return followerRepository.getUserFollows(username, pageable).map(this::projToUserResponse);
        }




        public List<UserResponse> getUserFollowersList(String username, Pageable pageable) throws UserException {

                Optional<User> user = userRepository.findByUsername(username);

                if(user.isEmpty()){
                        throw new UserException("No such user");
                }

                return followerRepository.getUserFollowersList(username, pageable).stream().map(this::projToUserResponse).collect(Collectors.toList());
        }

}
