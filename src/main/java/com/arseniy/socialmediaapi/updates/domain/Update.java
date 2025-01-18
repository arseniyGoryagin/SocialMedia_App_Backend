package com.arseniy.socialmediaapi.updates.domain;


import com.arseniy.socialmediaapi.user.domain.User;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@Entity
@Table(name = "updates_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "update_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
public class Update {


    public enum Type {
        Follow,
        Like,
        Comment
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;


    @Enumerated(EnumType.ORDINAL)
    @JsonValue
    Type type;

    @ManyToOne
    @JoinColumn()
    User user;


    @ManyToOne
    @JoinColumn()
    User from;

    LocalDateTime date;

    String message;

}
