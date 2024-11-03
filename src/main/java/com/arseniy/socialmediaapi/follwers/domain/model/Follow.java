package com.arseniy.socialmediaapi.follwers.domain.model;


import com.arseniy.socialmediaapi.user.domain.model.User;
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
@Table(name = "followers_table")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "target")
    private User target;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower")
    private User follower;

}
