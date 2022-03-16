package com.license.ProjectSocialNetwork.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class CommentId implements Serializable {
    Long id, postId, userId;
}
