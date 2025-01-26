package com.arseniy.socialmediaapi.user;


import com.arseniy.socialmediaapi.user.domain.UserResponse;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

        private final UserRepository userRepository;

        // TODO make this simpler and better
        private UserResponse userToUserResponse(User u, String currentUserUsername){
                return UserResponse.builder().
                        id(u.getId())
                        .name(u.getName())
                        .profilePicture(u.getProfilePicture())
                        .description(u.getDescription())
                        .followsCount(u.getFollows().size())
                        .followerCount(u.getFollowers().size())
                        .username(u.getUsername())
                        .isFollowing(u.getFollowers().contains(userRepository.findByUsername(currentUserUsername).get()))
                        .isOwn(Objects.equals(u.getUsername(), currentUserUsername))
                        .build();
        }

        public UserDetailsService userDetailsService() {
                return this::getByUsername;
        }

        public UserDetails getByUsername(@NotNull String username) {
               return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("no such user"));
        }

        public Optional<User> findByUsername(@NotNull String username) {
              return userRepository.findByUsername(username);
        }

        public UserResponse getUser(@NotNull String username, String currentUserUsername) throws NoSuchUserException {
                User user = findByUsername(username).orElseThrow(() -> new NoSuchUserException("User does not exist"));
                return userToUserResponse( user, currentUserUsername);
        }

        public Page<UserResponse> searchUsers(@NotNull String usernameSearch, @NotNull String currentUserUsername, Pageable pageable ) {
                return userRepository.findByUsernameStartingWith(usernameSearch, pageable).map(u -> userToUserResponse(u, currentUserUsername));
        }

        public Page<UserResponse> getUserFollowers(String username, String currentUserUsername, Pageable pageable){
                findByUsername(username).orElseThrow(() -> new NoSuchUserException("User does not exist"));
                return userRepository.findFollowersByUsername(currentUserUsername, pageable).map( u -> userToUserResponse(u, currentUserUsername));
        }

        public Page<UserResponse> getUserFollows(String username, String currentUserUsername, Pageable pageable) {
                findByUsername(username).orElseThrow(() -> new NoSuchUserException("User does not exist"));
                return userRepository.findFollowsByUsername(currentUserUsername, pageable).map( u -> userToUserResponse(u, currentUserUsername));
        }



        public List<Long> getUserFollowIds(String username){
                User user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchUserException("No such user"));
                return userRepository.findFollowsIdsByUsername(user.getId());
        }

}
