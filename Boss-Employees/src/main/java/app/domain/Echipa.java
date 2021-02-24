package app.domain;


import java.util.Objects;

public class Echipa extends Entity<Integer> {
    private String nume;
    private String rol;
    //private boolean activ;


    public Echipa(String nume, String rol)
    {
        this.nume = nume;
        this.rol = rol;
        //activ = true;
    }

//    public Echipa(String nume, String rol, boolean activ) {
//        this.nume = nume;
//        this.rol = rol;
//        this.activ = activ;
//    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Echipa))
            return false;
        Echipa echipa = (Echipa) obj;
        return Objects.equals(getNume(), echipa.getNume()) &&
                Objects.equals(getRol(), echipa.getRol());
    }

    @Override
    public String toString() {
        return nume + ' ' + rol;
    }
}
