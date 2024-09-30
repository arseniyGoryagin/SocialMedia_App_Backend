package com.arseniy.socialmediaapi.updates.domain.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Builder
@Entity
@Table(name = "updates_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "update_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor

@JsonSubTypes({
        @JsonSubTypes.Type(value = Update.FollowUpdate.class, name = "FOLLOWER"),
        @JsonSubTypes.Type(value = Update.LikeUpdate.class, name = "LIKE"),
        @JsonSubTypes.Type(value = Update.CommentUpdate.class, name = "COMMENT")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")

public class Update {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UpdateType type;


    @DiscriminatorValue("COMMENT")
    public static class CommentUpdate extends Update{


        private Long commentId;
        private String comment;
        private String username;

    }

    @DiscriminatorValue("FOLLOWER") // Value for this type of update
    public static class FollowUpdate extends Update{

        private String username;


    }


    @DiscriminatorValue("LIKE")
    public static class LikeUpdate extends Update{

        public Long postId;
        public String username;


    }

    static public enum UpdateType {

        LIKE, FOLLOW, COMMENT

    }




}
