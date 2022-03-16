package com.license.ProjectSocialNetwork.controller.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public abstract class BaseDTO <ID extends Serializable> implements Serializable {
    private ID id; // pk
}
