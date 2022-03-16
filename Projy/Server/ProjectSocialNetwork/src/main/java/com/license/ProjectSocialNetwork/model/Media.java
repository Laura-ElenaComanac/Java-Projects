package com.license.ProjectSocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
public class Media {
    @EmbeddedId
    private MediaId mediaId;
    private String mediaLink;

    @JsonBackReference
    @MapsId("postId")
    @JoinColumn(name = "postId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
