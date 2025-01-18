package com.arseniy.socialmediaapi.like.domain;


import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "likes_table", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_profile", "post"})})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "user_profile", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post", nullable = false)
    private Post post;

}
