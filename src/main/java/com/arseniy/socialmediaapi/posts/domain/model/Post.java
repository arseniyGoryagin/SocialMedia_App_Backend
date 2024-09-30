package com.arseniy.socialmediaapi.posts.domain.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "posts_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String title;
    private String body;

    private Long likes;

    private String username;
    private Boolean edited;


}
