package com.arseniy.socialmediaapi.user;


import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.followers.FollowerRepository;
import com.arseniy.socialmediaapi.user.domain.UserResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

        private final UserRepository userRepository;
        private final FollowerRepository followerRepository;


        private UserResponse userToUserResponse(User u, String currentUserUsername){
                return UserResponse.builder().
                        id(u.getId())
                        .name(u.getName())
                        .profilePicture(u.getProfilePicture())
                        .description(u.getDescription())
                        .followsCount(followerRepository.countByFollower_Username(u.getUsername()))
                        .followerCount(followerRepository.countByTarget_Username(u.getUsername()))
                        .username(u.getUsername())
                        .isFollowing(followerRepository.existsByTarget_UsernameAndFollower_Username(u.getUsername(), currentUserUsername))
                        .isOwn(Objects.equals(u.getUsername(), currentUserUsername))
                        .build();
        }


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


        public UserResponse getUser(@NotNull String username, String currentUserUsername) throws NoSuchException {

                Optional<User> user = findByUsername(username);

                if(user.isEmpty()){
                        throw new NoSuchException("No such user");
                }

                return userToUserResponse( user.get(), currentUserUsername);

        }

        public Page<UserResponse> searchUsers(@NotNull String usernameSearch, @NotNull String currentUserUsername, Pageable pageable ) throws NoSuchException {
                return userRepository.findByUsernameStartingWith(usernameSearch, pageable).map(u -> userToUserResponse(u, currentUserUsername));
        }


        public Page<UserResponse> getUserFollowers(String username, String currentUserUsername, Pageable pageable) throws NoSuchException {

                Optional<User> user = userRepository.findByUsername(username);

                if(user.isEmpty()){
                        throw new NoSuchException("No such user exception");
                }

                return followerRepository.findFollowerByTarget_Username(username, pageable).map( u -> userToUserResponse(u, currentUserUsername));
        }

        public Page<UserResponse> getUserFollows(String username, String currentUserUsername, Pageable pageable) throws NoSuchException {

                Optional<User> user = userRepository.findByUsername(username);

                if(user.isEmpty()){
                        throw new NoSuchException("No such user exception");
                }

                return followerRepository.findTargetByFollower_Username(username, pageable).map( u -> userToUserResponse(u, currentUserUsername));
        }

}
