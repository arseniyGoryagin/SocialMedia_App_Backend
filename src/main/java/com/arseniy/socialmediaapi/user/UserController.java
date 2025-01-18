package com.arseniy.socialmediaapi.user;


import com.arseniy.socialmediaapi.exceptions.EmailAlreadyInUseException;
import com.arseniy.socialmediaapi.exceptions.NoSuchException;
import com.arseniy.socialmediaapi.user.domain.UserResponse;
import com.arseniy.socialmediaapi.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}/followers")
    public ResponseEntity<Page<UserResponse>> getUserFollowers(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) throws NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        Pageable pageable = PageRequest.of(page, size);

        var userResponse = userService.getUserFollowers(username,currentUserUsername , pageable );

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<Page<UserResponse>> getUserFollows(@PathVariable("username") String username, @RequestParam("page") int page, @RequestParam("size") int size) throws  NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        Pageable pageable = PageRequest.of(page, size);

        var userResponse = userService.getUserFollows(username,currentUserUsername , pageable );

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username) throws EmailAlreadyInUseException,  NoSuchException {;

        String currentUserUsername = Util.getCurrentUserUsername();

        var userResponse = userService.getUser(username,  currentUserUsername);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


   /* @PutMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@RequestBody UserUpdateRequest request) throws EmailAlreadyInUseException,  NoSuchException {;

        String currentUserUsername = Util.getCurrentUserUsername();

       // var userResponse = userService.getUser(username,  currentUserUsername);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }*/




    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUser(@RequestParam("username") String searchUsername, @RequestParam("page") int page, @RequestParam("size") int size) throws NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        Pageable pageable = PageRequest.of(page, size);

        Page<UserResponse> users  = userService.searchUsers(searchUsername, currentUserUsername, pageable );

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() throws NoSuchException {

        String currentUserUsername = Util.getCurrentUserUsername();

        var userResponse = userService.getUser(currentUserUsername, currentUserUsername);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }



}
