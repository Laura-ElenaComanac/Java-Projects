package com.license.ProjectSocialNetwork.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode
public class PairEntity{
    @Id
    Tuple<Long,Long> pairId;
}

