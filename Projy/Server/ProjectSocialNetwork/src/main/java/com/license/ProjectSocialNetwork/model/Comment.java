package com.license.ProjectSocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Data @Builder
public class Comment{
    @EmbeddedId
    private CommentId commentId;
    private LocalTime date;
    private String content;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parentComment;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> replyComments = new ArrayList<>();

    @JsonBackReference
    @MapsId("postId")
    @JoinColumn(name = "postId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JsonBackReference
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
