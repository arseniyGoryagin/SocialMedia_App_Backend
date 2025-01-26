package com.arseniy.socialmediaapi.posts.domain;


import com.arseniy.socialmediaapi.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String body;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean edited;

    private LocalDateTime timePosted;

    @ManyToMany(mappedBy = "likes")
    @JsonIgnore
    private Set<User> liked = new HashSet<>();

}
