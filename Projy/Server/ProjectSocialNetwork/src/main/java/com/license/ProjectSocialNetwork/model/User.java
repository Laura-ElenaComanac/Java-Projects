package com.license.ProjectSocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Data @Builder
@ToString
public class User extends BaseEntity<Long> {
    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
//    @Enumerated(EnumType.STRING)
//    private Type type;
//    private String email;
//    private String location;
    private String gender;
//    private LocalDate birthday;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messagesFrom = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messagesTo = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "activeUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FollowRelation> activeUserRelations = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "followerUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FollowRelation> followerRelations = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender ;
    }
}
