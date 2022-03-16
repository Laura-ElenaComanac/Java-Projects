package com.license.ProjectSocialNetwork.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class Tuple<E1 extends Serializable, E2 extends Serializable> implements Serializable {
    private E1 e1;
    private E2 e2;

    public Tuple(E1 e1, E2 e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(e1, e2);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Tuple))
            return false;
        return this.e1.equals(((Tuple) obj).e1) && this.e2.equals(((Tuple) obj).e2);
    }

    @Override
    public String toString() {
        return "" + e1 + "," + e2;
    }
}