package com.arseniy.socialmediaapi.updates;


import com.arseniy.socialmediaapi.updates.domain.UpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/updates")
public class UpdateController {


    private final UpdateService updateService;


    @GetMapping()
    ResponseEntity<Page<UpdateResponse>> getUpdates(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("page") int page, @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UpdateResponse> updates = updateService.getUserUpdatesUpdates(userDetails.getUsername(), pageable);
        return new ResponseEntity<>(updates, HttpStatus.OK);
    }


}
