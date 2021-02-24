package app.domain;


import java.util.Objects;

public class Intrebare extends Entity<Integer> {
    private String descriere;
    private String v1;
    private String v2;
    private String v3;
    private String raspunsCorect;
    private int punctaj;

    public Intrebare(String descriere, String v1, String v2, String v3, String raspunsCorect, int punctaj)
    {
        this.descriere = descriere;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.raspunsCorect = raspunsCorect;
        this.punctaj = punctaj;
    }

    public int getNrIntrebare() {
        return super.getId();
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getRaspunsCorect() {
        return raspunsCorect;
    }

    public void setRaspunsCorect(String raspunsCorect) {
        this.raspunsCorect = raspunsCorect;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Intrebare))
            return false;
        Intrebare intrebare = (Intrebare) obj;
        return Objects.equals(getNrIntrebare(), intrebare.getNrIntrebare()) &&
                Objects.equals(getDescriere(), intrebare.getDescriere()) &&
                Objects.equals(getV1(), intrebare.getV1()) &&
                Objects.equals(getV2(), intrebare.getV2()) &&
                Objects.equals(getV3(), intrebare.getV3()) &&
                Objects.equals(getRaspunsCorect(), intrebare.getRaspunsCorect()) &&
                Objects.equals(getPunctaj(), intrebare.getPunctaj());
    }

    @Override
    public String toString() {
        return getNrIntrebare() + ' ' + descriere + ' ' + v1 + ' ' + v2 + ' ' + v3 + ' ' + raspunsCorect + ' ' + punctaj;
    }
}
