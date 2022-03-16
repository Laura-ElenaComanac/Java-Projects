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
public class Message {
    @EmbeddedId
    private MessageId messageId;
    private LocalTime date;
    private String content;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Message parentMessage;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> replyMessages = new ArrayList<>();

    @JsonBackReference
    @MapsId("fromUserId")
    @JoinColumn(name = "fromUserId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    @JsonBackReference
    @MapsId("toUserId")
    @JoinColumn(name = "toUserId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;
}
