package app.domain;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Client extends Entity<Long>{
    Long clientId;
    String name;
    Integer fidelityGrade;
    Integer varsta;
    public enum hobbies{reading, music, hiking, walking, extreme_sports};
    hobbies hobby;

    static AtomicLong identity = new AtomicLong(0);

    public Client(Long clientId, String name, Integer fidelityGrade, Integer varsta, hobbies hobby) {
        this.clientId = clientId;
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.varsta = varsta;
        this.hobby = hobby;

        this.setId(identity.incrementAndGet());
    }

    public Long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public Integer getFidelityGrade() {
        return fidelityGrade;
    }

    public Integer getVarsta() {
        return varsta;
    }

    public hobbies getHobby() {
        return hobby;
    }

    public static AtomicLong getIdentity() {
        return identity;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFidelityGrade(Integer fidelityGrade) {
        this.fidelityGrade = fidelityGrade;
    }

    public void setVarsta(Integer varsta) {
        this.varsta = varsta;
    }

    public void setHobby(hobbies hobby) {
        this.hobby = hobby;
    }

    public static void setIdentity(AtomicLong identity) {
        Client.identity = identity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId.equals(client.clientId) && name.equals(client.name) && fidelityGrade.equals(client.fidelityGrade) && varsta.equals(client.varsta) && hobby == client.hobby;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, name, fidelityGrade, varsta, hobby);
    }
}
