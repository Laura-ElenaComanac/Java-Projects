package app.domain;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Raspuns extends Entity<Integer> {
    private int nrIntrebare;
    private String numeStudent;
    private double punctaj;

    static AtomicInteger identity = new AtomicInteger(0);

    public Raspuns(int nrIntrebare, String numeStudent, double punctaj){
        this.nrIntrebare = nrIntrebare;
        this.numeStudent = numeStudent;
        this.punctaj = punctaj;

        this.setId(identity.incrementAndGet());
    }

    public int getNrIntrebare() {
        return nrIntrebare;
    }

    public void setNrIntrebare(int nrIntrebare) {
        this.nrIntrebare = nrIntrebare;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public double getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(double punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Raspuns))
            return false;
        Raspuns raspuns = (Raspuns) obj;
        return Objects.equals(getNrIntrebare(), raspuns.getNrIntrebare()) &&
                Objects.equals(getNumeStudent(), raspuns.getNumeStudent()) &&
                Objects.equals(getPunctaj(), raspuns.getPunctaj());
    }

    @Override
    public String toString() {
        return nrIntrebare + ' ' + numeStudent + ' ' + punctaj;
    }

}
