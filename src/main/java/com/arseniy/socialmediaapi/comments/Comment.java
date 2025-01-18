package com.arseniy.socialmediaapi.comments;


import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments_table")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile")
    private User user;

    private String body;

    private LocalDateTime timePosted;

    // TODO
    private Long likes;

    private Boolean edited;

}
