package com.license.ProjectSocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
public class FollowRelation extends BaseEntity<Long> {
    private LocalTime date;
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User activeUser;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User followerUser;
}
