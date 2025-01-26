package com.arseniy.socialmediaapi.user.domain;


import com.arseniy.socialmediaapi.posts.domain.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class User implements UserDetails {


    public enum Role{
        ADMIN, USER
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String password;
    private String username;
    private String name;
    private String description = "";
    private String profilePicture = "";


    private String email;
    private Role role;


    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn( name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "follows_id")
    )
    @JsonIgnore
    private Set<User> follows = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn( name = "follows_id"),
            inverseJoinColumns = @JoinColumn(name = "followers_id")
    )
    @JsonIgnore
    private Set<User> followers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn( name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likes = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    // Default
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
