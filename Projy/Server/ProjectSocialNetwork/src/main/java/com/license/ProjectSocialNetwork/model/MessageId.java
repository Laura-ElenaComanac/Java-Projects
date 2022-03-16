package com.license.ProjectSocialNetwork.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class MessageId implements Serializable {
    Long id, fromUserId, toUserId;
}
